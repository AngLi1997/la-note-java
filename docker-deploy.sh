set -e

echo "==== [1] 安装 Docker（可重复执行） ===="

if ! command -v docker &> /dev/null; then
  yum install -y yum-utils device-mapper-persistent-data lvm2
  yum-config-manager --add-repo https://mirrors.aliyun.com/docker-ce/linux/centos/docker-ce.repo
  yum install -y docker-ce-20.10.* docker-ce-cli-20.10.* containerd.io
  mkdir -p /etc/docker
fi

cat > /etc/docker/daemon.json <<EOF
{
  "registry-mirrors": ["https://g0b9eosa.mirror.aliyuncs.com", "https://registry.docker-cn.com"],
  "live-restore": true,
  "insecure-registries": ["172.16.0.4"]
}
EOF

systemctl enable docker
systemctl restart docker

echo "==== 检查并安装 docker-compose ===="

if ! command -v docker-compose &> /dev/null; then
  echo "未检测到 docker-compose，开始安装..."
  curl -L "https://github.com/docker/compose/releases/download/1.29.2/docker-compose-$(uname -s)-$(uname -m)" \
    -o /usr/local/bin/docker-compose
  chmod +x /usr/local/bin/docker-compose
  ln -s /usr/local/bin/docker-compose /usr/bin/docker-compose
fi

echo "==== [2] 安装 JDK（宿主机） ===="

if [ ! -d "/usr/java" ]; then
  mkdir -p /usr/java
  yum install -y java-1.8.0-openjdk java-1.8.0-openjdk-devel
fi

echo "==== [3] 创建 Docker 挂载目录 ===="

mkdir -p /root/docker/mysql
mkdir -p /root/docker/minio
mkdir -p /root/docker/nginx/html

echo "==== [4] 生成 Docker Compose 配置 ===="

cat > /root/la-note/docker-compose.yml <<EOF
version: '3'

services:
  mysql:
    image: mysql:8
    container_name: la-mysql
    restart: always
    environment:
      MYSQL_ROOT_PASSWORD: root
    ports:
      - "3306:3306"
    volumes:
      - /root/docker/mysql:/var/lib/mysql
    networks:
      - la-net

  minio:
    image: minio/minio
    container_name: la-minio
    restart: always
    environment:
      MINIO_ROOT_USER: minioadmin
      MINIO_ROOT_PASSWORD: minioadmin
    ports:
      - "9000:9000"
      - "9001:9001"
    volumes:
      - /root/docker/minio:/data
    command: server /data --console-address ":9001"
    networks:
      - la-net

  backend:
    build:
      context: /root/la-note/backend
      dockerfile: Dockerfile
    container_name: la-backend
    restart: always
    ports:
      - "8080:8080"
    depends_on:
      - mysql
    environment:
      SPRING_DATASOURCE_URL: jdbc:mysql://mysql:3306/la_note?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
      SPRING_DATASOURCE_USERNAME: root
      SPRING_DATASOURCE_PASSWORD: root
      JAVA_OPTS: "-Xms256m -Xmx512m"
    networks:
      - la-net

  nginx:
    image: nginx
    container_name: la-nginx
    restart: always
    ports:
      - "80:80"
    volumes:
      - /root/la-note/frontend/dist:/usr/share/nginx/html
      - /root/docker/nginx/nginx.conf:/etc/nginx/nginx.conf
    networks:
      - la-net

networks:
  la-net:
    driver: bridge
EOF

echo "==== [5] 生成后端 Dockerfile ===="

cat > /root/la-note/backend/Dockerfile <<EOF
FROM openjdk:8-jdk-alpine
VOLUME /tmp
COPY liang-note-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["sh", "-c", "java \$JAVA_OPTS -jar /app.jar"]
EOF

echo "==== [6] 生成 nginx.conf ===="

cat > /root/docker/nginx/nginx.conf <<EOF
worker_processes 1;

events {
    worker_connections 1024;
}

http {
    include       mime.types;
    default_type  application/octet-stream;
    sendfile        on;
    keepalive_timeout  65;
    client_max_body_size 100M;

    server {
        listen       80;
        server_name  localhost;

        location / {
            root   /usr/share/nginx/html;
            index  index.html index.htm;
            try_files  $uri $uri/ /index.html;
        }

        location /api/ {
            proxy_pass http://la-backend:8080/api/;
        }
    }
}
EOF

echo "==== [7] 构建后端镜像 ===="
docker-compose -f /root/la-note/docker-compose.yml build backend

echo "==== [8] 启动所有服务 ===="
docker-compose -f /root/la-note/docker-compose.yml up -d

echo "✅ 部署完成！
- 后端接口: http://<server-ip>:8080
- 前端地址: http://<server-ip>
- MinIO: http://<server-ip>:9000
- MySQL: root / root
- MinIO: minio / minio
"

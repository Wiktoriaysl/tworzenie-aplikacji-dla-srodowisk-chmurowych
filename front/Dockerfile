#Stage 1
FROM node:20-alpine as builder
WORKDIR /app
COPY package*.json .
RUN npm install
COPY . .
RUN npm run build

#Stage 2
FROM nginx:1.19.0
WORKDIR /usr/share/nginx/html
RUN rm -rf ./*
COPY --from=builder /app/build .

COPY ./env.sh .
COPY .env .
RUN chmod +x env.sh

EXPOSE 80
# ENTRYPOINT ["nginx", "-g", "daemon off;"]
CMD ["/bin/sh", "-c", "/usr/share/nginx/html/env.sh && nginx -g \"daemon off;\""]
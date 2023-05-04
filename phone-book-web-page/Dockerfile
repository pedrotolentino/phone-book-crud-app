FROM node:latest as node-angular
WORKDIR /app
COPY . .
RUN npm install
RUN npm run build --prod

FROM nginx:alpine
COPY nginx.config /etc/nginx/conf.d/default.conf
COPY --from=node-angular /app/dist/phone-book-web-page /usr/share/nginx/html
# ---------- STAGE 1: builder ----------
FROM node:22.13.1-bullseye-slim AS builder
ENV DEBIAN_FRONTEND=noninteractive
WORKDIR /app

RUN npm install -g pnpm@10.21.0

COPY package.json pnpm-lock.yaml* ./

RUN pnpm install --frozen-lockfile

COPY . .
RUN pnpm build

FROM node:22.13.1-bullseye-slim AS runner
WORKDIR /app

RUN npm install -g serve

COPY --from=builder /app/dist ./dist

EXPOSE 3000

CMD ["serve", "-s", "dist", "-l", "3000"]
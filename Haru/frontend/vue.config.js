const CopyWebpackPlugin = require("copy-webpack-plugin");
const path = require("path");

module.exports = {
  outputDir:'../src/main/resources/static', // 추가함
  devServer: {
    proxy: {
      "/api": {
        target: "http://13.209.4.24:3000",
        changeOrigin: true,
        pathRewrite: {
          "^/api": "",
        },
      },
    },
    client: {
      overlay: false,
    },
  },
  configureWebpack: {
    plugins: [
      new CopyWebpackPlugin({
        patterns: [
          {
            from: path.resolve(__dirname, "src/img"), // Vue 프로젝트의 img 폴더 경로
            to: path.resolve(__dirname, "../src/main/resources/static/img"), // Spring Boot 프로젝트의 static 폴더 경로
            globOptions: {
              ignore: [".*"],
            },
          },
        ],
      }),
    ],
  },
};

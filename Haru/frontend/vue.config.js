const CopyWebpackPlugin = require("copy-webpack-plugin");
const path = require("path");

module.exports = {
  devServer: {
    proxy: {
      "/api": {
        target: "http://localhost:80",
        changeOrigin: true,
        pathRewrite: {
          "^/api": "",
        },
      },
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

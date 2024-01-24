const { defineConfig } = require("@vue/cli-service");
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
    client: {
      overlay: false
    }
  },
};
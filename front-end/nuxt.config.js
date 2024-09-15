export default {
  compatibilityDate: "2024-09-15",

  devServer: {
    port: 3001,
  },

  modules: ["@nuxtjs/tailwindcss", "unplugin-fonts/nuxt", "@nuxtjs/color-mode"],

  ssr: false,

  //To support the dark/light mode theme using @nuxtjs/color-mode
  colorMode: {
    classSuffix: "",
    preference: "system",
    fallback: "dark",
  },

  app: {
    head: {
      link: [
        {
          rel: "icon",
          type: "image/x-icon",
          href: `/img/favicon.ico`,
        },
      ],
    },
  },

  //Get the config from .env files
  runtimeConfig: {
    apiSecret: "123",
    public: {
      apiBase: process.env.API_URL || "http://localhost:8080/",
    },
  },

  // auto import components
  components: [
    {
      path: "~/components",
      pathPrefix: false,
    },
  ],

  extends: [],

  css: [],

  build: {
    postcss: {
      postcssOptions: {
        plugins: {
          tailwindcss: {},
          autoprefixer: {},
        },
      },
    },
  },
};

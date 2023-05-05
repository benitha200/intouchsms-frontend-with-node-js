// const { createProxyMiddleware } = require("http-proxy-middleware");
// const { ONLINE_API_URL } = require("./Constants/Index")
import { createProxyMiddleware } from "http-proxy-middleware";
import { ONLINE_API_URL } from "./Constants/Index";

module.exports = app => {
    app.use(
        createProxyMiddleware('/generatetoken', {
            target: 'https://intouchsms.co.rw/api',
            changeOrigin: true
        })
    );
    app.use(
        createProxyMiddleware('/logout', {
            target: 'https://intouchsms.co.rw/api',
            changeOrigin: true
        })
    );
    app.use(
        createProxyMiddleware('/sendsms/.json',{
            target:'https://intouchsms.co.rw/api',
            changeOrigin:true
        })
    )
    app.use(
        createProxyMiddleware('/getmyprofile', {
            target: ONLINE_API_URL,
            changeOrigin: true
        })
    );
    app.use(
        createProxyMiddleware('/appecashtopup', {
            target: ONLINE_API_URL,
            changeOrigin: true
        })
    );
    app.use(
        createProxyMiddleware('/getmmnetworks', {
            target: ONLINE_API_URL,
            changeOrigin: true
        })
    );
    app.use(
        createProxyMiddleware('/getuserunitprice', {
            target: ONLINE_API_URL,
            changeOrigin: true
        })
    )
    app.use(
        createProxyMiddleware('/getpublishedpackages', {
            target: ONLINE_API_URL,
            changeOrigin: true
        })
    );
    app.use(
        createProxyMiddleware('/getsendernames', {
            target: ONLINE_API_URL,
            changeOrigin: true
        })
    );
};

// const express = require('express');
// const { createProxyMiddleware } = require('http-proxy-middleware');

// const app = express();

// app.use('/generatetoken', createProxyMiddleware({ target: 'https://intouchsms.co.rw/api', changeOrigin: true }));
// app.use('/logout', createProxyMiddleware({ target: 'https://intouchsms.co.rw/api', changeOrigin: true }));
// app.listen(3000);
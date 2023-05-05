const express = require('express');
const bodyParser = require('body-parser');
const cors = require('cors');
const { createProxyMiddleware } = require('http-proxy-middleware');

const ONLINE_API_URL = 'https://intouchsms.co.rw/api';

const app = express();
app.use(bodyParser.json());
app.use(cors());

// app.use('/api', createProxyMiddleware({ target: 'http://localhost:3000', changeOrigin: true }));
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

app.listen(5000, () => {
  console.log('Server started on port 5000');
});
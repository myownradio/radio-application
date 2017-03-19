const ExtractTextPlugin = require("extract-text-webpack-plugin");
const webpack = require("webpack");

const extractStyles = new ExtractTextPlugin("styles.css");

const cssLoaderString = 'css-loader?modules&importLoaders=1&localIdentName=[name]__[local]___[hash:base64:5]';

module.exports = {
    entry: "./src/index.js",
    output: {
        path: '../../../target/classes/static/',
        filename: "app.bundle.js"
    },
    module: {
        loaders: [
            {
                test: /\.js$/,
                exclude: /(node_modules)/,
                loader: 'babel-loader'
            },
            {
                test: /\.css$/,
                loader: extractStyles.extract([
                    cssLoaderString,
                    'postcss-loader'
                ])
            },
            {
                test: /\.less$/,
                loader: extractStyles.extract([
                    cssLoaderString,
                    'postcss-loader',
                    'less-loader'
                ])
            }
        ]
    },
    plugins: [
        extractStyles
    ]
};
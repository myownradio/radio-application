const ExtractTextPlugin = require("extract-text-webpack-plugin");

const extractStyles = new ExtractTextPlugin("styles.css");

module.exports = {
    entry: "./src/index.js",
    output: {
        path: '../../../target/classes/static/',
        filename: "bundle.js"
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
                loader: extractStyles.extract(['css-loader'])
            },
            {
                test: /\.less$/,
                loader: extractStyles.extract(['css-loader', 'less-loader'])
            }
        ]
    },
    plugins: [
        extractStyles
    ]
};
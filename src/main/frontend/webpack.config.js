module.exports = {
    entry: "./src/app.js",
    output: {
        path: '../../../target/classes/static/',
        filename: "bundle.js"
    },
    module: {
        loaders: [
            {
                test: /\.js$/,
                exclude: /(node_modules|bower_components)/,
                loader: 'babel-loader',
                query: {
                    presets: ['env', 'stage-0', 'es2016', 'react'],
                    plugins: ['syntax-flow', 'transform-runtime']
                }
            },
        ]
    }
};
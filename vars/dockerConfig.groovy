def nodeJs(Map params) {
    return [
        image: ${params.image ?: 'node:22-alpine'},
        reuseNode: true
    ]
}

return this

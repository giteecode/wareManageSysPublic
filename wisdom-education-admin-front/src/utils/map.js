let Map = {
  BaiDuMap (ak) {
    return new Promise(function (resolve, reject) {
      window.init = function () {
        // eslint-disable-next-line no-undef
        resolve(BMap)
      }
      const script = document.createElement('script')
      script.type = 'text/javascript'
      script.src = 'http://api.map.baidu.com/api?v=2.0&ak=' + ak + '&callback=init'
      script.onerror = reject
      document.head.appendChild(script)
    })
  }

}

export {
  Map
}

UE.registerUI('fillBlank', function(editor, uiname){

   var iconUrl = editor.options.UEDITOR_HOME_URL + 'plugin/fillBlank.png';

 /* editor.ready(function(){
    UE.utils.cssRule('fillBlank', 'img.fillBlank{vertical-align: middle;}', editor.document);
  });*/

  /*editor.ready(function(){
    UE.utils.cssRule('fillBlank', 'img.kfformula{vertical-align: middle;}', editor.document);
  });*/

 // let iconUrl = "./fillBlank.png"
  var kfBtn = new UE.ui.Button({
    name:'插入填空' + uiname,
    title:'插入填空',
    //需要添加的额外样式，指定icon图标
    cssRules :'margin-top: 2px;  background: url("' + iconUrl + '") no-repeat !important',
    onclick: function () {
      editor.execCommand('insertHtml', '<span style="display: inline-block;' +
        ' font-weight: bold;  margin: 5px; color: red">__________</span>');
     // editor.execCommand('insertHtml', '<span style="display: inline-block; text-align: center; width: 90px; border-bottom: 2px solid red; font-weight: bold;  margin: 5px; color: red">1</span>');// insertNode('<span style="display: inline-block; color: red">________</span>')
     // let content = editor.getContent() + '<span style="display: inline-block; color: red">________</span>'
     // editor.setContent(content)
    }
  });
  return kfBtn;
});


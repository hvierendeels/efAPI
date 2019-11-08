"use strict"
//allow popup's in chrome!!
//in chrome , hovering on tab opened window: bezig met laden!?

//todo button reload so you can reload with hidden fields
//todo button hise : hide all selected headerfields!!!
const enclosing_div="enclosing_div";//chrome uncaught SyntaxError: Identifier 'enclosing_div' has already been declared
const exclFields=
{
"invoices":["external_invoice_id","type","currency","uri"]
,
"items":["invoice_id","uri","stockitem_id","general_ledger_account"]
,
"events":["invoice_id","uri"]
,
"recipients":["external_event_recipient_id"]
,
"payments":["invoice_id","uri"]
};
var menu=null;
var contextMenuForTd=null;
var table;
var cellIndex=-1;
var rowToExpand=null;
var tr0;
var myWindow=[];
var trace=false;//can be set by window.trace=true
function myOnLoad(tblId){
 if(window.IWASHERE){
  console.log("IWASHERE");
  console.trace();
  //provoke_error();
  /**********/
   /**********/
 }
 else{
  console.log("myOnLoad");
  window.IWASHERE=true;
  window.onerror=myOnError;//no alert on error in firefox
  window.onbeforeunload=myOnbeforeUnload;

  contextMenuForTd=document.getElementById("contextMenuForTd");
  if(contextMenuForTd==null){
   alert('no contextMenuForTd');
   return;
  }
  contextMenuForTd.style.position="absolute";//move to iwasnotherebefore
  //menu=document.getElementById(menuId);
  menu=document.getElementById("contextMenu");
  if(menu==null){
   alert('no menu');
   return;
  }
  menu.style.position="absolute";
  addListeners();
  myOnLoad1(tblId);
 }//iwasnotherebefore
  
 //console.log("window=",window);
 //console.log("document=",document);

 /******************** 
 contextMenuForTd=document.getElementById("contextMenuForTd");
 if(contextMenuForTd==null){
  alert('no contextMenuForTd');
  return;
 }
 contextMenuForTd.style.position="absolute";//move to iwasnotherebefore
 //menu=document.getElementById(menuId);
 menu=document.getElementById("contextMenu");
 if(menu==null){
  alert('no menu');
  return;
 }
 menu.style.position="absolute";
 *************************/
 /*******************
 table=document.getElementById(tblId);
 if(table==null){
  alert('no table for '+tblId);
  return;
 }
 var theads=table.getElementsByTagName('thead');
 if(! theads || theads.length>1){
  alert('invalid table theads='+theads);
  return
 }
 var th0=theads[0];
 tr0=th0.firstElementChild;
 //console.log("tr0=",tr0);
 *****************/

 //moved addListeners();
 //console.log("table=",table);
 //console.log("menu=",menu);
}//myOnLoad
function myOnLoad1(tblId){
 console.log("myOnLoad1");
 table=document.getElementById(tblId);
 if(table==null){
  alert('no table for '+tblId);
  return;
 }
 var theads=table.getElementsByTagName('thead');
 if(! theads || theads.length>1){
  alert('invalid table theads='+theads);
  return
 }
 var th0=theads[0];
 tr0=th0.firstElementChild;
 //console.log("tr0=",tr0);
}//myOnLoad1

function addEvent(obj,type,fn){
 var resp;
 var ename=type.replace(/^on/i,"");
 //console.log("ename=",ename);
 if(obj.attachEvent){
  //console.log("attachEvent");
  //resp=obj.attachEvent("on"+ename,function(){return fn.call(obj,window.event)});
  resp=obj.attachEvent("on"+ename,fn);
  if(!resp){
   obj["on"+ename]=fn;
   //console.log("obj on");
  }
  //console.log("resp=",resp);
 }
 else if(obj.addEventListener){
  //console.log("addEventListener");
  obj.addEventListener(ename,fn,false);//resp is null!, false or true : no difference
 }
 return obj;
}//addevent
function addListeners(){
 //addEvent(window,'click',elseClicked);
 addEvent(document,'click',elseClicked);
 addEvent(document,"contextmenu",showContextMenu);
 addEvent(menu,'click',menuClicked);
 addEvent(contextMenuForTd,'click',contextMenuForTdClicked);
 /**************
 if(table){
  var theads=table.getElementsByTagName('thead');
  if(! theads || theads.length>1){
   alert('invalid table theads='+theads);
   return
  }
  var th0=theads[0];
  tr0=th0.firstElementChild;
  //console.log("tr0=",tr0);
 }//table
 ************/
}//addListeners
function preDivHtml(windowName){
 var rv=""; 
 rv+="<html>";
 rv+="<head>";
 rv+="<title>"+windowName+"</title>";
 rv+="<link rel=\"stylesheet\" href=\"test.css\">";
 rv+="<script src=\"test.js\"></script>";
 rv+="</head>";
 rv+="<body>";
 rv+="<div class=\"menu\" id=\"contextMenu\" style=\"display:none\">";
 rv+="<ul>";
 rv+="<li id=\"hide\">hide</li>";
 rv+="<li id=\"none\">none</li>";
 rv+="</ul>";
 rv+="</div>";
 rv+="<div class=\"menu\" id=\"contextMenuForTd\" style=\"display:none\">";
 rv+="<ul>";
 rv+="</ul>";
 rv+="</div>";
 return(rv);
}//preDivHtml
function postDivHtml(windowName){
 var rv="";
 rv+="<script>";        
 //rv+="window.addEventListener(\"load\",myOnLoad,false);";//does not seem to work
 //rv+="myOnLoad('"+windowName+"',null,true);";//todo attachlistener onload
 rv+="myOnLoad('"+windowName+"');";
 //rv+="window.onload=myOnLoad";
 rv+="</script>";
 rv+="</body>";
 rv+="</html>";
 return(rv);
}//postDivHtml
function makeHtmlForNewWindow(wnm,idText,innerText,iwh){
 var rv="";
 var json_objs=null;
 var fieldnames=[];
 var excl_fields=exclFields[wnm];
 if(!excl_fields) excl_fields=[];
 //console.log("excl_fields=",excl_fields);
 if(typeof innerText=='string'){
  json_objs=JSON.parse(innerText);
 }
 else json_objs=innerText;
 if(!(json_objs instanceof Array)){
  alert(json_objs+" not Array");
  return(null);
 }//Array
 if(!iwh){
  rv+=preDivHtml(wnm);
 }
 rv+="<div id='"+enclosing_div+"'>";
 rv+="<h1>"+wnm+" for "+idText+"</h1>";
 rv+="<div id=\"jsonText\">";
 //rv+=innerText;
 rv+="</div>";
 rv+="<table id='"+wnm+"' border=\"1\" cellpadding=\"0\" cellspacing=\"0\">";
 rv+="<thead>";
 rv+="<tr>";
 //todo get values via name!!!
 var hnms=[];
 var obj0=json_objs[0];
 var hnms_i=0;
 for(let p in obj0){
  let skip=false;
  for(let z=0;z<excl_fields.length;z++){
   if(p==excl_fields[z]){
    //console.log("skipping "+p);
    skip=true;
    break;
   }
  }//z
  if(skip) continue;
  hnms[hnms_i]=p;
  hnms_i++;
  let v=obj0[p];
  if(typeof v=="object"){
   rv+="<th class=\"List\">";
   rv+=p;
   rv+="</th>";
  }
  else{
   rv+="<th>";
   rv+=p;
   rv+="</th>";
  }
 }//let p
 rv+="</tr>";
 rv+="</thead>";
 rv+="<tbody>";
 for(var i in json_objs){
  var o=json_objs[i];
  if(typeof o!='object'){
   alert("typeof o not object "+o);
  }
  rv+="<tr>";
  //for(let p in o){
  for(let k=0;k<hnms.length;k++){
   //let v=o[p];
   let v=o[hnms[k]];
   if(typeof v=="object"){
    let v_s=JSON.stringify(v);
    //console.log("v_s="+v_s);
    let cnm="List";
    if(v_s.length==2){
     console.log("empty List for "+o[hnms[k]]);
     cnm="emptyList";
    }//2
    //rv+="<td class=\"List\">";
    rv+="<td class=\""+cnm+"\">";
    //rv+=JSON.stringify(v);
    rv+=v_s;
    rv+="</td>";
   }
   else{
    rv+="<td>";
    rv+=v;
    rv+="</td>";
   }
  }//let k
  rv+="</tr>";
 }//for i
 rv+="</tbody>";
 rv+="</table>";
 //rv+=innerText;
 /***********/
 /************/
 rv+="</div>";
 if(!iwh){
  rv+=postDivHtml(wnm);
 }
 else{
  rv+="<script>";        
  rv+="myOnLoad1('"+wnm+"');";
  //rv+="window.addEventListener(\"load\",myOnLoad,false);";
  rv+="</script>";
 }
 return(rv);
}//makeHtmlForNewWindow
function elseClicked(evnt){
 if(menu) menu.style.display="none";
 contextMenuForTd.style.display="none";
}//elseClicked
function openNewWindow(wnm){
 //console.log("wnm="+wnm);
 var wbo=null;//window before open
 var wao=null;//window after open
 var pos=-1;
 var cnds=tr0.childNodes;

 for(let i=0;i<cnds.length;i++){
  if(cnds[i].innerText==wnm){
   pos=i;
   break;
  }
 }//for
 //console.log("pos="+pos);
 var cnd=rowToExpand.childNodes[pos];
 var idText=rowToExpand.childNodes[0].innerText;//id in column 0, todo cannor be hidden!
 //console.log("cnd="+cnd);
 wbo=myWindow[wnm];
 console.log("myWindow "+wnm+" ",wbo);
 if(wbo){
  if(wbo.closed){
   console.log("window was closed "+wnm+" "+(typeof wbo));//wnm_h object but no toString()!?
  }
  else{
   console.log("window was open "+wnm);
   //alert("window was open "+wnm+" ec="+wnm_h.enclosing_div);
  }
 }//wbo
 myWindow[wnm]=window.open("",wnm,"width=800,height=400,scrollbars=yes,resizable=yes,status=yes,location=no,menubar=yes,toolbar=yes");
 wao=myWindow[wnm];
 //console.log("iwashere "+wao.IWASHERE);
 /**/
 try{
  let dcmnt=wao.document;//SecurityError: Permission denied to access property "document" on cross-origin object
  //dcmnt.body
  //dcmnt.documentElement
  let ediv=dcmnt.getElementById(enclosing_div);
  if(ediv){
   ediv.remove();
  }
 }//try
 catch(err){
  console.log("err=",err);
 }//catch
 /**/
 let rv=makeHtmlForNewWindow(wnm,idText,cnd.innerText,wao.IWASHERE);
 wao.document.write(rv);
 contextMenuForTd.style.display="none";
}//openNewWindow
function contextMenuForTdClicked(evnt){
 //console.log("contextMenuTdClicked",evnt);
 var m_i=evnt.target;
 if(m_i.tagName!="LI"){
  //alert("not LI but "+target.tagName);//clicked in text of menu
  return;
 }
 //console.log("contextMenuTdClicked",m_i);
 openNewWindow(m_i.id);
 contextMenuForTd.style.display="none";
}//contextMenuTdClicked
function menuClicked(evnt){
 //console.log("this=",this);//this = div contextMenu
 //console.log("evnt=",evnt);
 if(evnt==null) evnt=window.event;
 //console.log("evnt=",evnt);
 var target=evnt.target;
 if(target==null) target=evnt.srcElement;
 //console.log("target=",target);
 if(target.tagName=="LI"){
  if(target.id=="hide"){
   hideColumn(table.id,cellIndex);
  }
 }//li
 else{
  //clicking in menu outside of li !!
  //console.log('clicked menu target.tagName='+target.tagName);
  //return
 }
 menu.style.display="none";
 contextMenuForTd.style.display="none";
 if(evnt.preventDefault) evnt.preventDefault();
 else evnt.cancelBubble=true;
 if(evnt.stopPropagation) evnt.stopPropagation();
}//menuClicked
function showContextMenuForTd(target){
 //console.log("showContextMenuForTd target",target);
 //console.log("showContextMenuForTd",contextMenuForTd);
 let uls=contextMenuForTd.getElementsByTagName('ul');
 //console.log("uls",uls);
 //var fc=contextMenuForTd.firstChild;//!!! text
 while(uls[0].childNodes[0]){
  //console.log("removing child 0 ",uls[0].childNodes[0]);
  uls[0].removeChild(uls[0].childNodes[0]);
 }
 var mn_items=[];
 var pnd=target.parentNode;
 //console.log("showContextMenuForTd pnd",pnd);
 if(pnd.localName=="tr"){
  rowToExpand=pnd;
  var cnds=pnd.childNodes;
  for(let i=0;i<cnds.length;i++){
   var cnd=cnds[i];
   if(cnd.className=="List"){
    let tl=cnd.innerText.length;
    if(tl<=2) continue;
    let cnm=tr0.childNodes[i].innerText;
    //console.log("cnm="+cnm);
    mn_items.push(cnm);
    let item=document.createElement("li");
    item.appendChild(document.createTextNode(cnm));
    item.id=cnm;
    //console.log("item=",item);
    uls[0].appendChild(item);
   }//List
  }//for
  if(mn_items.length>0){
   var box=target.getBoundingClientRect();
   var left=box.x?box.x:box.left;
   var top=box.y?box.y:box.top;
   var scrollx=window.scrollX?window.scrollX:document.body.scrollLeft;
   var scrolly=window.scrollY?window.scrollY:document.body.scrollTop;
   left=left+scrollx;
   top=top+scrolly;
   contextMenuForTd.style.left=left;
   //contextMenuForTd.style.top=box.y?box.y:box.top;
   contextMenuForTd.style.top=top;
   contextMenuForTd.style.display="block";
  }//mn_items
  else{
   console.log("no List to expand");
  }
 }//tr
 return(false);
}//showContextMenuForTd(target)
function showContextMenuForTh(target){
  var box=target.getBoundingClientRect();//DOMRect The Element.getBoundingClientRect() method returns the size of an element and its position relative to the viewport. If you need the bounding rectangle relative to the top-left corner of the document, just add the current scrolling position to the top and left properties (these can be obtained using window.scrollX and window.scrollY) to get a bounding rectangle which is independent from the current scrolling position.
  //console.log("box=",box);
  //menu.style.left=evnt.pageX;
  //menu.style.top=evnt.pageY;
  var left=box.x?box.x:box.left;
  //alert("left="+left+" pageX="+evnt.pageX+" pageY="+evnt.pageY+" pageXOffset="+window.pageXOffset+" de scrollleft="+document.documentElement.scrollLeft+" db left="+document.body.scrollLeft+" scrollx="+window.scrollX);
  //scrollLeft=0 in firefox
  //var scrollx=window.scrollX?window.scrollX:document.documentElement.scrollLeft;
  var scrollx=window.scrollX?window.scrollX:document.body.scrollLeft;
  //console.log("window scrollX=",scrollx);
  left=left+scrollx;
  //console.log("left",left);
  menu.style.left=left;
  menu.style.top=box.y?box.y:box.top;
  menu.style.display="block";
  cellIndex=target.cellIndex;
  //console.log("cellIndex=",cellIndex);
  /*************
  if(evnt.preventDefault) evnt.preventDefault();
  if(evnt.stopPropagation) evnt.stopPropagation();
  else evnt.cancelBubble=true;
  *******************/
  return(false);
}//showContextMenuForTh
function showContextMenu(evnt){
 var rv=false;
 if(evnt==null){
  alert("evnt=null");
  evnt=window.event;//ie8?
 }
 //console.log("evnt=",evnt);
 //console.log("this=",this);//this = thead
 var target=evnt.target;//th
 if(target==null) target=evnt.srcElement;//ie8
 //console.log("showContextMenu target=",target);
 if(target.localName=="th"){
  contextMenuForTd.style.display="none";
  if(target.cellIndex!=0){
   rv=showContextMenuForTh(target);
  }
 }//th
 else if(target.localName=="td"){
  if(menu) menu.style.display="none";
  if(target.cellIndex==0){
   rv=showContextMenuForTd(target);
  }
  else rv=true;
 }//td
 else{
  rv=true;
 }

 /*************************
 var box=target.getBoundingClientRect();//DOMRect The Element.getBoundingClientRect() method returns the size of an element and its position relative to the viewport. If you need the bounding rectangle relative to the top-left corner of the document, just add the current scrolling position to the top and left properties (these can be obtained using window.scrollX and window.scrollY) to get a bounding rectangle which is independent from the current scrolling position.
 //console.log("box=",box);
 //menu.style.left=evnt.pageX;
 //menu.style.top=evnt.pageY;
 var left=box.x?box.x:box.left;
 //alert("left="+left+" pageX="+evnt.pageX+" pageY="+evnt.pageY+" pageXOffset="+window.pageXOffset+" de scrollleft="+document.documentElement.scrollLeft+" db left="+document.body.scrollLeft+" scrollx="+window.scrollX);
 //scrollLeft=0 in firefox
 //var scrollx=window.scrollX?window.scrollX:document.documentElement.scrollLeft;
 var scrollx=window.scrollX?window.scrollX:document.body.scrollLeft;
 //console.log("window scrollX=",scrollx);
 left=left+scrollx;
 //console.log("left",left);
 menu.style.left=left;
 menu.style.top=box.y?box.y:box.top;
 menu.style.display="block";
 **************************/
 /**********
 var parentNode=target.parentNode;
 while(parentNode){
  var tagName=parentNode.tagName;
  //console.log("tagName="+tagName);
  if(tagName=="TABLE"){
   break;
  }
  parentNode=parentNode.parentNode;
 }
 ****************/
 /***** 
 cellIndex=target.cellIndex;
 //console.log("cellIndex=",cellIndex);
 ****************/
 if(rv==false){
  if(evnt.preventDefault) evnt.preventDefault();
  if(evnt.stopPropagation) evnt.stopPropagation();
  else evnt.cancelBubble=true;
 }//rv
 return(rv);//false for IE8 to not display system context menu!
}//showContextMenu
function hideColumn(tId,cnr)
{
var elmnt=document.getElementById(tId);var nodes=elmnt.childNodes;for(var i=0;i<nodes.length;i=i+1){var node=nodes[i];if(node.nodeName=='THEAD' || node.nodeName=='TBODY'){var trs=node.childNodes;for(var trsi=0;trsi<trs.length;trsi++){var tr=trs[trsi];if(tr.nodeName=='TR'){var td_teller=0;var tds=tr.childNodes;for(var tdsi=0;tdsi<tds.length;tdsi++){var td=tds[tdsi];if(td.nodeName=='TD' || td.nodeName=='TH'){if(td_teller==cnr){td.style.display='none';}td_teller++;}}}}}}
}
function myOnbeforeUnload(evnt){
 //console.log("myOnbeforeUnload evnt=",evnt);
 //console.log("myOnbeforeUnload myWindow=",myWindow);
 //alert("myOnbeforeUnload");//is not shown!?
 //console.log("myWindow.length=",myWindow.length);//lenth==0!
 for(let wndw in myWindow){
  console.log("wndw=",wndw);
  myWindow[wndw].close();
 }//for
 //sleep(5000);
}//myOnbeforeUnload
function myOnError(msg,url,lineNo,columnNo,error){
 //make sure no errors are made here or ending up with infinite loop
 alert("myOnError:"+msg+" "+url+" "+lineNo+" "+columnNo+" "+error);
}//myOnError
function sleep(delay){
 var start = new Date().getTime();
 while (new Date().getTime() < start + delay);
}//sleep

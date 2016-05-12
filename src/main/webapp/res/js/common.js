function json$ref(json) {
  var $ = json, o = {o: $};
  function parse$ref(o, attr) {
    var obj = o[attr];
    if ("object" === typeof obj) {
      var ref = obj["$ref"];
      if (ref && "string" === typeof ref && ref.indexOf("$.") === 0) {
        o[attr] = eval(ref);//set
      } else {
        for (var i in obj) {
          parse$ref(obj, i);
        }
      }
    }
    return obj;
  }
  parse$ref(o, "o");
  return $;
}

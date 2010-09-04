function loadVolumeToRigntPanelFn($rightPanelContent) {   
    var jsonObj = $rightPanelContent.data("jsonObj");
    
    var $rightPanelContent = $("#right_panel_content");
    
    $rightPanelContent.find("#id").text(jsonObj.id);
    $rightPanelContent.find("#name").text(jsonObj.name);    
    $rightPanelContent.find("#zonename").text(jsonObj.zonename);    
    $rightPanelContent.find("#device_id").text(jsonObj.deviceid);   
    $rightPanelContent.find("#state").text(jsonObj.state);    
    $rightPanelContent.find("#storage").text(jsonObj.storage);
    $rightPanelContent.find("#account").text(jsonObj.account); 
    
    $rightPanelContent.find("#type").text(noNull(jsonObj.type) + " (" + noNull(jsonObj.storagetype) + " storage)");
    $rightPanelContent.find("#size").text((jsonObj.size == "0") ? "" : convertBytes(jsonObj.size));		
    
    if (jsonObj.virtualmachineid == null) 
		$rightPanelContent.find("#vm_name").text("detached");
	else 
		$rightPanelContent.find("#vm_name").text(getVmName(jsonObj.vmname, jsonObj.vmdisplayname) + " (" + jsonObj.vmstate + ")");
		
    setDateField(jsonObj.created, $rightPanelContent.find("#created"));	
}
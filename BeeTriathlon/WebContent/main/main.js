
$(document).ready(function() {

	$("#downupthings").animate({
		top : '10px'
	}, 3000);

	$("img[src ^='farmstatus/seedmsg']").hide();

	$("#trfield1").click(function() {
		$("table[id ^='menu']").hide();
		$("#menu1").toggle();
	});
	$("#trfield2").click(function() {
		$("table[id ^='menu']").hide();
		$("#menu2").toggle();
	});
	$("#trfield3").click(function() {
		$("table[id ^='menu']").hide();
		$("#menu3").toggle();
	});
	$("#trlumberjack").click(function() {
		$("table[id ^='menu']").hide();
		$("#behavior").val("lumberjacking");
		$("img[src ^='mong/co']").hide();
		$("#colum").show();
	});
	$("#trbeekeep").click(function() {
		$("table[id ^='menu']").hide();
		$("#behavior").val("beekeeping");
		$("img[src ^='mong/co']").hide();
		$("#cobee").show();
	});
	$("#trwoodchop").click(function() {
		$("table[id ^='menu']").hide();
		$("#behavior").val("woodchopping");
		$("img[src ^='mong/co']").hide();
		$("#cochop").show();
	});
	$("#trswim").click(function() {
		$("table[id ^='menu']").hide();
		$("#menu7").toggle();
	});
	$("#trhouse").click(function() {
		$("table[id ^='menu']").hide();
		$("#behavior").val("homesleeping");
		$("img[src ^='mong/co']").hide();
		$("#zzz").show();
	});
	$("#trhike").click(function() {
		$("table[id ^='menu']").hide();
		$("#behavior").val("hike");
		$("img[src ^='mong/co']").hide();
		$("#cohike").show();

	});
	$("#trchinup").click(function() {
		$("table[id ^='menu']").hide();
		$("#behavior").val("chinup");
		$("img[src ^='mong/co']").hide();
		$("#cochin").show();
	});

	$("#sowi0").click(function() {
		$("#menusowing0").show();
	});
	$("#sowi1").click(function() {
		$("#menusowing1").show();
	});
	$("#sowi2").click(function() {
		$("#menusowing2").show();
	});

	$("#spra0").click(function() {
		$("table[id ^='menu']").hide();
		$("#behavior").val("sprayi00");
		$("img[src ^='mong/co']").hide();
		$("#co53").show();
	});
	$("#spra1").click(function() {
		$("table[id ^='menu']").hide();
		$("#behavior").val("sprayi10");
		$("img[src ^='mong/co']").hide();
		$("#co63").show();
	});
	$("#spra2").click(function() {
		$("table[id ^='menu']").hide();
		$("#behavior").val("sprayi20");
		$("img[src ^='mong/co']").hide();
		$("#co73").show();
	});

	$("#drai0").click(function() {
		$("table[id ^='menu']").hide();
		$("#behavior").val("draini00");
		$("img[src ^='mong/co']").hide();
		$("#co53").show();
	});
	$("#drai1").click(function() {
		$("table[id ^='menu']").hide();
		$("#behavior").val("draini10");
		$("img[src ^='mong/co']").hide();
		$("#co63").show();
	});
	$("#drai2").click(function() {
		$("table[id ^='menu']").hide();
		$("#behavior").val("draini20");
		$("img[src ^='mong/co']").hide();
		$("#co73").show();
	});

	$("#fert0").click(function() {
		$("table[id ^='menu']").hide();
		$("#behavior").val("fertil00");
		$("img[src ^='mong/co']").hide();
		$("#co53").show();
	});
	$("#fert1").click(function() {
		$("table[id ^='menu']").hide();
		$("#behavior").val("fertil10");
		$("img[src ^='mong/co']").hide();
		$("#co63").show();
	});
	$("#fert2").click(function() {
		$("table[id ^='menu']").hide();
		$("#behavior").val("fertil20");
		$("img[src ^='mong/co']").hide();
		$("#co73").show();
	});

	$("#harv0").click(function() {
		$("table[id ^='menu']").hide();
		$("#behavior").val("harves00");
		$("img[src ^='mong/co']").hide();
		$("#co53").show();
	});
	$("#harv1").click(function() {
		$("table[id ^='menu']").hide();
		$("#behavior").val("harves10");
		$("img[src ^='mong/co']").hide();
		$("#co63").show();
	});
	$("#harv2").click(function() {
		$("table[id ^='menu']").hide();
		$("#behavior").val("harves20");
		$("img[src ^='mong/co']").hide();
		$("#co73").show();
	});

	$("img[id ^='sow']").mouseleave(function() {
		$("img[src ^='farmstatus/seedmsg']").hide();
	});

	$("#sow00").click(function() {
		$("table[id ^='menu']").hide();
		$("#behavior").val("sowing00");
		$("img[src ^='mong/co']").hide();
		$("#co53").show();
	});
	$("#sow00").mouseenter(function() {
		$("img[src ^='farmstatus/seedmsg']").hide();
		$("img[src ='farmstatus/seedmsg0.png']").show();
	});
	$("#sow01").click(function() {
		$("table[id ^='menu']").hide();
		$("#behavior").val("sowing01");
		$("img[src ^='mong/co']").hide();
		$("#co53").show();
	});
	$("#sow01").mouseenter(function() {
		$("img[src ^='farmstatus/seedmsg']").hide();
		$("img[src ='farmstatus/seedmsg1.png']").show();
	});
	$("#sow02").click(function() {
		$("table[id ^='menu']").hide();
		$("#behavior").val("sowing02");
		$("img[src ^='mong/co']").hide();
		$("#co53").show();
	});
	$("#sow02").mouseenter(function() {
		$("img[src ^='farmstatus/seedmsg']").hide();
		$("img[src ='farmstatus/seedmsg2.png']").show();
	});
	$("#sow03").click(function() {
		$("table[id ^='menu']").hide();
		$("#behavior").val("sowing03");
		$("img[src ^='mong/co']").hide();
		$("#co53").show();
	});
	$("#sow03").mouseenter(function() {
		$("img[src ^='farmstatus/seedmsg']").hide();
		$("img[src ='farmstatus/seedmsg3.png']").show();
	});
	$("#sow04").click(function() {
		$("table[id ^='menu']").hide();
		$("#behavior").val("sowing04");
		$("img[src ^='mong/co']").hide();
		$("#co53").show();
	});
	$("#sow04").mouseenter(function() {
		$("img[src ^='farmstatus/seedmsg']").hide();
		$("img[src ='farmstatus/seedmsg4.png']").show();
	});

	$("#sow10").click(function() {
		$("table[id ^='menu']").hide();
		$("#behavior").val("sowing10");
		$("img[src ^='mong/co']").hide();
		$("#co63").show();
	});
	$("#sow10").mouseenter(function() {
		$("img[src ^='farmstatus/seedmsg']").hide();
		$("img[src ='farmstatus/seedmsg0.png']").show();
	});
	$("#sow11").click(function() {
		$("table[id ^='menu']").hide();
		$("#behavior").val("sowing11");
		$("img[src ^='mong/co']").hide();
		$("#co63").show();
	});
	$("#sow11").mouseenter(function() {
		$("img[src ^='farmstatus/seedmsg']").hide();
		$("img[src ='farmstatus/seedmsg1.png']").show();
	});
	$("#sow12").click(function() {
		$("table[id ^='menu']").hide();
		$("#behavior").val("sowing12");
		$("img[src ^='mong/co']").hide();
		$("#co63").show();
	});
	$("#sow12").mouseenter(function() {
		$("img[src ^='farmstatus/seedmsg']").hide();
		$("img[src ='farmstatus/seedmsg2.png']").show();
	});
	$("#sow13").click(function() {
		$("table[id ^='menu']").hide();
		$("#behavior").val("sowing13");
		$("img[src ^='mong/co']").hide();
		$("#co63").show();
	});
	$("#sow13").mouseenter(function() {
		$("img[src ^='farmstatus/seedmsg']").hide();
		$("img[src ='farmstatus/seedmsg3.png']").show();
	});
	$("#sow14").click(function() {
		$("table[id ^='menu']").hide();
		$("#behavior").val("sowing14");
		$("img[src ^='mong/co']").hide();
		$("#co63").show();
	});
	$("#sow14").mouseenter(function() {
		$("img[src ^='farmstatus/seedmsg']").hide();
		$("img[src ='farmstatus/seedmsg4.png']").show();
	});

	$("#sow20").click(function() {
		$("table[id ^='menu']").hide();
		$("#behavior").val("sowing20");
		$("img[src ^='mong/co']").hide();
		$("#co73").show();
	});
	$("#sow20").mouseenter(function() {
		$("img[src ^='farmstatus/seedmsg']").hide();
		$("img[src ='farmstatus/seedmsg0.png']").show();
	});
	$("#sow21").click(function() {
		$("table[id ^='menu']").hide();
		$("#behavior").val("sowing21");
		$("img[src ^='mong/co']").hide();
		$("#co73").show();
	});
	$("#sow21").mouseenter(function() {
		$("img[src ^='farmstatus/seedmsg']").hide();
		$("img[src ='farmstatus/seedmsg1.png']").show();
	});
	$("#sow22").click(function() {
		$("table[id ^='menu']").hide();
		$("#behavior").val("sowing22");
		$("img[src ^='mong/co']").hide();
		$("#co73").show();
	});
	$("#sow22").mouseenter(function() {
		$("img[src ^='farmstatus/seedmsg']").hide();
		$("img[src ='farmstatus/seedmsg2.png']").show();
	});
	$("#sow23").click(function() {
		$("table[id ^='menu']").hide();
		$("#behavior").val("sowing23");
		$("img[src ^='mong/co']").hide();
		$("#co73").show();
	});
	$("#sow23").mouseenter(function() {
		$("img[src ^='farmstatus/seedmsg']").hide();
		$("img[src ='farmstatus/seedmsg3.png']").show();
	});
	$("#sow24").click(function() {
		$("table[id ^='menu']").hide();
		$("#behavior").val("sowing24");
		$("img[src ^='mong/co']").hide();
		$("#co73").show();
	});
	$("#sow24").mouseenter(function() {
		$("img[src ^='farmstatus/seedmsg']").hide();
		$("img[src ='farmstatus/seedmsg4.png']").show();
	});
	$("#swim").click(function() {
		$("#behavior").val("swim");
		$("img[src ^='mong/co']").hide();
		$("#coswim").show();
		$("#menu7").hide();
	});
	$("#fish").click(function() {
		$("#behavior").val("fishing");
		$("img[src ^='mong/co']").hide();
		$("#cofish").show();
		$("#menu7").hide();
	});
});
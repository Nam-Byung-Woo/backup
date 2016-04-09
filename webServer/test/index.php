<?php
include "HTMLParser.php"; 

error_reporting(E_ALL);
ini_set("display_errors", 1);

$year = $_POST["year"];
$month = strlen($_POST["month"]) < 2 ? "0".$_POST["month"] : $_POST["month"];
$day = strlen($_POST["day"]) < 2 ? "0".$_POST["day"] : $_POST["day"];

$url = 'http://ntry.com/stats/api.php?offset=0&size=288&cmd=get_round_data&type=ladder&get_type=date&get_value='.$year.'-'.$month.'-'.$day; 

if($url != ''){
    $iup = new HTMLParser();
    $iup->setUrl($url);
    
    //파싱결과를 돌려줌
    $result = $iup->getResult();

	$str = "I am a [boy] your a gril";
	preg_match('#\[(.*?)\]#', $iup->getBuffer(), $word);
	$data = $word[0];

	echo $data;
}

?>
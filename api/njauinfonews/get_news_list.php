<?php

require 'db.php';
require 'models.php';

header('Access-Control-Allow-Origin:*');

$tid = 0;
if(isset($_GET['tid'])){
    $tid = $_GET['tid'];
}

$ps = 0;
if(isset($_GET['ps'])){
    $ps = $_GET['ps'];
}

$pn = 0;
if(isset($_GET['pn'])){
    $pn = $_GET['pn'];
}

$sql = 'select * from news ';
if ($tid >=1 && $tid <= 6) {
    $sql = $sql.' where tid = '.$tid.' ';
}

if ($ps > 20) {
    $ps = 20;
}

if ($ps <= 0) {
    $ps = 1;
}

if ($pn <= 0) {
    $pn = 1;
}

$offset = ($pn - 1) * $ps;

$sql = $sql.'order by ctime desc limit '.$offset.', '.$ps;

$newsList = [];
if ($rows = $conn->query($sql)) {
    while ($row = $rows->fetch(PDO::FETCH_ASSOC)) {
        $news = new NewsBrief();
        
        $news->id = intval($row['id']);
        $news->title = $row['title'];
        $news->author = $row['author'];
        $news->ctime = $row['ctime'];
        // $news->content = $row['content'];
        $news->tid = intval($row['tid']);
        $news->iid = intval($row['iid']);
        $news->sid = intval($row['sid']);
        $news->nid = intval($row['nid']);
        $news->pid = intval($row['pid']);

        $newsList[] = $news;
    }
}

echo (json_encode($newsList, JSON_UNESCAPED_UNICODE));
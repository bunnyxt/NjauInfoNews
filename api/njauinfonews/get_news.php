<?php

require 'db.php';
require 'models.php';

header('Access-Control-Allow-Origin:*');

$id = 0;
if(isset($_GET['id'])){
    $id = $_GET['id'];
}

$sql = 'select * from news where id = '.$id;

$news = null;
if ($rows = $conn->query($sql)) {
    if ($row = $rows->fetch(PDO::FETCH_ASSOC)) {
        $news = new News();

        $news->id = intval($row['id']);
        $news->title = $row['title'];
        $news->author = $row['author'];
        $news->ctime = $row['ctime'];
    
        $news->content = base64_encode($row['content']);
    
        $news->tid = intval($row['tid']);
        $news->iid = intval($row['iid']);
        $news->sid = intval($row['sid']);
        $news->nid = intval($row['nid']);
        $news->pid = intval($row['pid']);
    } 
}

echo (json_encode($news, JSON_UNESCAPED_UNICODE));
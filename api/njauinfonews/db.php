<?php

try {
    $conn = new PDO('mysql:host=localhost;dbname=njau_info_news;charset=utf8','root','root');
}
catch(PDOException $e)
{
    echo ('<script>alert("'.$e->getMessage().'")</script>');
    die;
}
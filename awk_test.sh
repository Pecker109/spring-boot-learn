#!/bin/sh

#awk -F"," '{if($3==0)print$1}' /Users/apple/Desktop/tradeday_test.csv | sort | grep -A 1 "20210102" | tail -n 1

#step1:拷贝交易日 csv

function_next_trade_day(){
  echo "当前日期 ${date_str},获取下一交易日"

  #判断 tradeday.csv 文件是否存在
  data_path_local_pre="/Users/apple/Desktop/"
  trade_day_file_path=$data_path_local_pre/tradeday_test.csv

    if [ ! -e "$trade_day_file_path" ]; then
        echo "$trade_day_file_path 文件不存在,退出"
        exit
    fi

  # tradeday.csv 第 3 列为 0-交易日
  next_trade_day=$(awk -F"," '{if($3==0)print$1}' $trade_day_file_path | sort | grep -A1 ${date_str} | tail -n1)
  if [ -z "$next_trade_day" ]; then
      echo "当前日期 ${date_str} 非交易日,退出"
      exit
  fi
  echo "下一交易日: $next_trade_day"
  return ${next_trade_day}
}

next_trade_day=""
#date_str="$(date +%Y%m%d)"
date_str="20210101"
function_next_trade_day
echo $next_trade_day
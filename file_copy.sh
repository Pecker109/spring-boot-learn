#!/bin/sh
# 输入 :
# -l:必填,本地路径
# -a:必填,远程路径,即资管文件地址格式如:gold@10.10.10.10:/base/file
# -s:必填,需要包含"{data_str}",清算文件路径,即从 Windows 机子上挂载过来的路径
# 示例: gold_init_file_copy.sh -l /tmp/gold/data -a gold@10.10.10.10/base/file -s /Users/apple/Desktop/basefile/{date_str}/storage
# crontab -e 配置示例: */1 * * * * /home/gold/test/test.sh > /home/gold/test/load.log 2>&1

date "+%Y-%m-%d %H:%M:%S"

data_path_local_pre=""
asset_manage_file_path=""
storage_file_path=""

# 文件拷贝
copy_file(){
  echo "创建本地路径 $data_path_local"
  mkdir -p "$data_path_local"
  # 资管文件远程拷贝到日期目录下 scp(手工配置免密登录)
  echo "拷贝资管文件 src = $asset_manage_file_path"
  scp -r $asset_manage_file_path "$data_path_local"

  # 清算目录拷贝到日期目录下:(替换掉"date_str")
  storage_file_path=$(echo $storage_file_path |sed 's/'"{date_str}"'/'"${date_str}"'/g')
  echo "拷贝清算文件 src = $storage_file_path"
  cp -r $storage_file_path "$data_path_local"
}

# 移除文件名中日期后缀 : CLIENT_POSITION_20220101.TXT->CLIENT_POSITION.TXT
remove_file_date(){
  file_path=$1
  file_name=$2

  if [[ ${file_name} =~ ${date_str} ]]
  then
    rename=$(echo $file_name |sed 's/'"_${date_str}"'//g')
    echo "文件名包含日期后缀需重命名:$file_name -> $rename"
    mv $file_path$file_name $file_path$rename
  fi
}

# 文件重命名
rename_file() {
  file_list=$(ls $data_path_local)
  for file in $file_list ; do
    remove_file_date $data_path_local $file
  done
}

# 参数检查
all_params="$@"
echo "输入参数为: $all_params"
all_params_filter=$(echo "$all_params"|grep "\-l"|grep "\-s"|grep "\-a")
if [ -z "$all_params_filter" ]; then
    echo "缺少参数项,请检查输入是否包含:-l -a -s "
    exit
fi
# 参数赋值
while getopts "l:s:a:" optname
do
    case "$optname" in
      "l")
        data_path_local_pre=$OPTARG/
        echo "-l 本地地址: $data_path_local_pre"
        ;;
      "s")
        storage_file_path=$OPTARG/*
        echo "-s 清算文件地址: $storage_file_path"
        ;;
      "a")
        asset_manage_file_path=$OPTARG/*
        echo "-a 资管文件地址: $asset_manage_file_path"
        ;;
      *)
        echo "无效的输入项"
        ;;
    esac
done

# 本地路径
date_str="$(date +%Y%m%d)"
data_path_local=$data_path_local_pre$date_str/
echo "本地路径 $data_path_local"

# 文件拷贝
copy_file
# 文件重命名
rename_file



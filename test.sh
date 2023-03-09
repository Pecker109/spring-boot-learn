#!/bin/sh
date_str="$(date +%Y%m%d)"

# 移除文件名中日期后缀 : CLIENT_POSITION_20220101.TXT->CLIENT_POSITION.TXT
remove_file_data(){
  file_path=$1
  file_name=$2

  if [[ ${file_name} =~ ${date_str} ]]
  then
    rename=$(echo $file_name |sed 's/'"_${date_str}"'//g')
    echo "文件名包含日期后缀需重命名:$file_name -> $rename"
    mv $file_path$file_name $file_path$rename
#  else
#    echo "no"
  fi
}


copy_file(){
  # 资管文件远程拷贝到日期目录下 scp(手工配置免密登录)
  asset_manage_file_path="gold@10.10.10.10/base/file"

  mkdir "$data_path_local"
  echo "本地路径 $data_path_local"
  #scp $asset_manage_file_path
  # 清算目录拷贝到日期目录下,去除日期后缀
  storage_file_path="/Users/apple/Desktop/gateway/goldfile/"
  #cp -r $storage_file_path "$data_path_local"
}


rename_file() {
        file_list=$(ls $data_path_local)
        for file in $file_list ; do
                echo "文件名 $file"
                remove_file_data $data_path_local $file
        done
}

# 输入 :
# 本地路径 -l
# 远程路径,即资管文件地址格式如:gold@10.10.10.10:/base/file -r
# 清算文件路径,即从 Windows 机子上挂载过来的路径 -s

# 本地路径前缀 -l
data_path_pre="/Users/apple/Desktop/basefile/"
# 本地路径
data_path_local=$data_path_pre$date_str/

copy_file

rename_file




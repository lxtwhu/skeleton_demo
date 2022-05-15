#!/bin/bash

#*************************************************************
# Function:公共方法
# Inputs:  无
# Author:  yxwang1
# Date:    20180810
# History: 0.1
#**************************************************************



exbeeline(){
 exebeeline -c ${ClusterNm} -n ${KUser} --hiveconf mapreduce.job.queuename=${QueueNm}  "$@"
}


dokinit()
{
username=$1
password=$2
PID=$$
if [ -n "$username" -a -n "$password" ];then
  kinit -p $username -c /tmp/krb5_cache_${username}_${PID} <<EOF
$password
EOF
  if [ $? -eq 0 ];then
    export KRB5CCNAME=/tmp/krb5_cache_${username}_${PID}
  else
    log_skdError "[101] Error: user $username Kinit failed!"
    return 101
  fi
elif [ -n "$username" ];then
  kinit -p ${username} -kt ${KEYTAB} -c /tmp/krb5_cache_${username}_${PID}
  if [ $? -eq 0 ];then
    export KRB5CCNAME=/tmp/krb5_cache_${username}_${PID}
  else
    log_skdError "[101] Error: user ${username} Kinit failed"
    return 101
  fi

else
  kinit -p ${KUser} -kt ${KEYTAB} -c /tmp/krb5_cache_${DEFAULT_PRINCIPAL}_${PID}
  if [ $? -eq 0 ];then
    export KRB5CCNAME=/tmp/krb5_cache_${DEFAULT_PRINCIPAL}_${PID}
  else 
    log_skdError "[101] Error: default user ${DEFAULT_PRINCIPAL} Kinit failed"
    return 101
  fi
fi
}


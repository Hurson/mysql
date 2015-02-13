#ifndef   AVIT_DATAMANAGER
#define    AVIT_DATAMANAGER

/*
  函数名:avit_datamanager
  参数:msg 指向字符串的指针，table_ver是UNT的版本号，des_ver是0X88描述符的版本号，unt_path是保存ts文件的路径，格式如
       /avit/dvb/data/    log_path是指生成的日志文件路径，格式如 /avit/dvb/log/createUNT.log
  返回值:成功返回0，失败返回-1
  函数的作用:接受广告系统的XML消息，分析消息，打包TS
*/
int avit_createUNT(const char *xml_data, int table_ver, int des_ver, const char *unt_path, const char *log_path);

#endif


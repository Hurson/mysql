#ifndef   AVIT_DATAMANAGER
#define    AVIT_DATAMANAGER

/*
  ������:avit_datamanager
  ����:msg ָ���ַ�����ָ�룬table_ver��UNT�İ汾�ţ�des_ver��0X88�������İ汾�ţ�unt_path�Ǳ���ts�ļ���·������ʽ��
       /avit/dvb/data/    log_path��ָ���ɵ���־�ļ�·������ʽ�� /avit/dvb/log/createUNT.log
  ����ֵ:�ɹ�����0��ʧ�ܷ���-1
  ����������:���ܹ��ϵͳ��XML��Ϣ��������Ϣ�����TS
*/
int avit_createUNT(const char *xml_data, int table_ver, int des_ver, const char *unt_path, const char *log_path);

#endif


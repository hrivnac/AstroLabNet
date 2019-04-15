#!/bin/zsh
# Generate Entry class for FX Java Table
# astrolabnet.topology.1
# com.astrolabsoftware.AstroLabNet
# ../build
########################################
TABLE=${1}
PACKAGE=${2}
DIR=${3}
TABLENAME=`echo ${TABLE} | awk -F. '{print $2}'`
T="${(C)TABLENAME}"
ENTRYCLASS="${DIR}/`echo ${PACKAGE} | sed 's#\.#/#g'`/${T}Entry.java"
echo "Generating ${ENTRYCLASS} from ${TABLE}"
echo "scan \"${TABLE}\"" | /opt/hbase/bin/hbase shell | grep 'column=' | while read L; do echo $L | awk '{print $2}' | sed 's/column=//g' | sed 's/,//g'; done | sort | uniq | tr '\n' ' ' | read COLS
COLS="x:key ${COLS}"
LASTCOL=`echo ${COLS} | awk '{print $NF}'`

# Entry
/bin/rm -f "${ENTRYCLASS}"
echo "package ${PACKAGE};" > "${ENTRYCLASS}"
echo "import com.astrolabsoftware.AstroLabNet.HBaser.TableEntry;" >> "${ENTRYCLASS}"
echo "import org.apache.log4j.Logger;" >> "${ENTRYCLASS}"
echo "" >> "${ENTRYCLASS}"
echo "// Generated class - do not edit" >> "${ENTRYCLASS}"
echo "" >> "${ENTRYCLASS}"
echo "public class ${T}Entry implements TableEntry {" >> "${ENTRYCLASS}"
echo "" >> "${ENTRYCLASS}"
echo "public ${T}Entry() {}" >> "${ENTRYCLASS}"
echo "" >> "${ENTRYCLASS}"
echo "public ${T}Entry(" >> "${ENTRYCLASS}"
setopt shwordsplit                                                                                              
for X in ${COLS}; do
  #echo "${X}" | awk -F: '{print $2}' | read V
  echo "${X}" | sed 's/://g' | read V
  if [[ "${X}" == "${LASTCOL}" ]]; then
    echo "String ${V}" >> "${ENTRYCLASS}"
  else
    echo "String ${V}," >> "${ENTRYCLASS}"
    fi 
  done
echo ")" >> "${ENTRYCLASS}"
echo "{" >> "${ENTRYCLASS}"
for X in ${COLS}; do
  #echo ${X} | awk -F: '{print $2}' | read V
  echo ${X} | sed 's/://g' | read V
  echo "_${V} = ${V};" >> "${ENTRYCLASS}"
  done
echo "}" >> "${ENTRYCLASS}"
echo "" >> "${ENTRYCLASS}"
for X in ${COLS}; do
  #echo ${X} | awk -F: '{print $2}' | read V
  echo ${X} | sed 's/://g' | read V
  echo "public void set${(C)V}(String ${V}) {_${V} = ${V};}" >> "${ENTRYCLASS}"
  echo "" >> "${ENTRYCLASS}"
  echo "public String get${(C)V}() {return _${V};}" >> "${ENTRYCLASS}"
  echo ""  >> "${ENTRYCLASS}"
  echo "private String _${V} = null;" >> "${ENTRYCLASS}"
  echo "" >> "${ENTRYCLASS}"
  done
echo "@Override" >> "${ENTRYCLASS}"
echo "public void set(String name, String value) {" >> "${ENTRYCLASS}"
echo "switch (name) {" >> "${ENTRYCLASS}"
for X in ${COLS}; do
  #echo ${X} | awk -F: '{print $2}' | read V
  echo ${X} | sed 's/://g' | read V
  echo "case \"${X}\":" >> "${ENTRYCLASS}"
  echo "set${(C)V}(value);" >> "${ENTRYCLASS}"
  echo "break;"   >> "${ENTRYCLASS}"
  done  
echo "default:" >> "${ENTRYCLASS}"
echo "log.error(\"Cannot show \" + name + \" = \" + value);" >> "${ENTRYCLASS}"
echo "}" >> "${ENTRYCLASS}"
echo "}" >> "${ENTRYCLASS}"
echo "" >> "${ENTRYCLASS}"
echo "@Override" >> "${ENTRYCLASS}"
echo "public void reset() {" >> "${ENTRYCLASS}"
for X in ${COLS}; do
  #echo ${X} | awk -F: '{print $2}' | read V
  echo ${X} | sed 's/://g' | read V
  echo "_${V} = null;" >> "${ENTRYCLASS}"
  done  
echo "}" >> "${ENTRYCLASS}"
echo "" >> "${ENTRYCLASS}"
echo "public static String[] ENTRY_NAMES = new String[]{" >> "${ENTRYCLASS}"
for X in ${COLS}; do
  #echo ${X} | awk -F: '{print $2}' | read V
  echo ${X} | sed 's/://g' | read V
  if [[ "${X}" == "${LASTCOL}" ]]; then
    echo "\"${V}\"" >> "${ENTRYCLASS}"
  else
    echo "\"${V}\"," >> "${ENTRYCLASS}"
    fi 
  done
echo "};" >> "${ENTRYCLASS}"
echo "" >> "${ENTRYCLASS}"
echo "private static Logger log = Logger.getLogger(${T}Entry.class);" >> "${ENTRYCLASS}"
echo "" >> "${ENTRYCLASS}"
echo "}" >> "${ENTRYCLASS}"
unsetopt shwordsplit

source build-global.properties
source build.properties

if [[ "x" = "x${ANT_HOME}" ]]; then
  export ANT_HOME="${ant}"
  export PATH="${ANT_HOME}/bin:${PATH}"
  fi

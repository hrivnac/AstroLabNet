/opt/hbase/bin/hbase shell ../src/ruby/createCatalog.rb
/opt/hbase/bin/hbase shell ../src/ruby/createJournal.rb
/opt/hbase/bin/hbase shell ../src/ruby/createTopology.rb
/opt/hbase/bin/hbase shell ../src/ruby/fillTopology.rb
java -jar ../lib/AvroReader-*.exe.jar http://localhost:8080 ../../fink-broker/datasim


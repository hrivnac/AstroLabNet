# Fill Topology
###############
# Local Host
put 'astrolabnet.topology.1', 'Local Host', 'i:name',     'Local Host'
put 'astrolabnet.topology.1', 'Local Host', 'i:location', 'here'
put 'astrolabnet.topology.1', 'Local Host', 'd:spark',    'http://localhost:8998'
put 'astrolabnet.topology.1', 'Local Host', 'd:livy',     'http://localhost:4040'
put 'astrolabnet.topology.1', 'Local Host', 'd:hbase',    'http://localhost:8080'
put 'astrolabnet.topology.1', 'Local Host', 'c:comment',  'Default server'
# LAL
put 'astrolabnet.topology.1', 'LAL', 'i:name',     'LAL'
put 'astrolabnet.topology.1', 'LAL', 'i:location', 'Orsay'
put 'astrolabnet.topology.1', 'LAL', 'd:spark',    'http://vm-75222.lal.in2p3.fr:21111'
put 'astrolabnet.topology.1', 'LAL', 'd:livy',     'http://vm-75222.lal.in2p3.fr:20001'
put 'astrolabnet.topology.1', 'LAL', 'd:hbase',    'http://134.158.74.54:8080'
put 'astrolabnet.topology.1', 'LAL', 'c:comment',  'Institute server'

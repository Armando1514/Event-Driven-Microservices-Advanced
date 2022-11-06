kafka-topics --bootstrap-server my-release-kafka:9092 --create --if-not-exists --topic payment-request --replication-factor 1 --partitions 3
kafka-topics --bootstrap-server my-release-kafka:9092 --create --if-not-exists --topic payment-response --replication-factor 1 --partitions 3
kafka-topics --bootstrap-server my-release-kafka:9092 --create --if-not-exists --topic restaurant-approval-request --replication-factor 1 --partitions 3
kafka-topics --bootstrap-server my-release-kafka:9092 --create --if-not-exists --topic restaurant-approval-response --replication-factor 1 --partitions 3
kafka-topics --bootstrap-server my-release-kafka:9092 --create --if-not-exists --topic customer --replication-factor 1 --partitions 3
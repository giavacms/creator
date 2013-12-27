#!/bin/bash
echo "--------------------------------------------" >> $OPENSHIFT_DATA_DIR/cron_jboss/cron.log
date >> $OPENSHIFT_DATA_DIR/cron_jboss/cron.log
NOW=$(date +"%H")
echo $NOW >> $OPENSHIFT_DATA_DIR/cron_jboss/cron.log
#RESTO=$(($NOW%2))
#CNFR=$(($RESTO -eq0))
#echo 'resto' $RESTO '- cnfr' $CNFR >> $OPENSHIFT_DATA_DIR/cron_jboss/cron.log
if [ "$NOW" -eq "21" ];then
	$OPENSHIFT_WILDFLY_DIR/bin/control restart;
	echo "restart jboss:" >> $OPENSHIFT_DATA_DIR/cron_jboss/cron.log
	rm $OPENSHIFT_WILDFLY_DIR/standalone/log/server.log.*
	echo "deleting old logs:" >> $OPENSHIFT_DATA_DIR/cron_jboss/cron.log
else 
	echo "non e' il momento di riavviare jboss" >> $OPENSHIFT_DATA_DIR/cron_jboss/cron.log
fi
date >> $OPENSHIFT_DATA_DIR/cron_jboss/cron.log
echo "--------------------------------------------" >> $OPENSHIFT_DATA_DIR/cron_jboss/cron.log

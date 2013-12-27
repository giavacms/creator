GiavaCMS on OpenShift
=========================

This is the GiavaCMS JBoss Quickstart app.  You can find more info @ https://github.com/giavacms/giavacms

Running on OpenShift
--------------------

Create an account at http://openshift.redhat.com/

Create a jbossas-7 application

    rhc app-create giavaCMS jbossas-7 --from-code git://github.com/giavacms/projectxxxxxxx.git

That's it, you can now checkout your application at:

    http://giavaCMS-$namespace.rhcloud.com

MySQL as a backend
-----------------------
By default, this giavaCMS uses MySQL as the backend.

To do this, add MySQL cartridge to your application:

    rhc cartridge add mysql-5.1 -a giavaCMS

The giavaCMS will start in update mode (see the persistence.xml in deployments/ROOT.war/WEB-INF/classes/META-INF/persistence.xml):
after first deploy, to become more faster your application, change this property:

      <property name="hibernate.hbm2ddl.auto" value="update"/>
to:
      <!-- property name="hibernate.hbm2ddl.auto" value="update"/ !-->


Commit this change, and push.

The OpenShift `jbossas` cartridge
-----------------------

The OpenShift `jbossas` cartridge documentation can be found at:

https://github.com/openshift/origin-server/tree/master/cartridges/openshift-origin-cartridge-jbossas/README.md

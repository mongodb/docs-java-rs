.. important:: 

    The {+driver-short+} does not support :ref:`UnixServerAddress
    <{+core-api+}/UnixServerAddress.html>`_ objects or domain socket
    connections. To use a domain socket to connect, use the :driver:`Java Sync
    driver. </java/sync/current/>` Otherwise, use a :ref:`ServerAddress
    <{+core-api+}/ServerAddress.html>`_ object to connect from the
    {+driver-short+}.
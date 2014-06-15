"use strict";
require.config({
    paths: {
        'QUnit': '../qunit/qunit-1.14.0'
    },
    shim: {
       'QUnit': {
           exports: 'QUnit',
           init: function() {
               QUnit.config.autoload = false;
               QUnit.config.autostart = false;
           }
       } 
    }
});

// require the unit tests.
require(
    ['QUnit', 'ContactsManagerTests'],
    function(QUnit, contactsManagerTests) {
        // run the tests.
    	contactsManagerTests.run();
        // start QUnit.
        QUnit.load();
        QUnit.start();
    }
);

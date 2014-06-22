"use strict";
require.config({
	paths : {
		'QUnit' : '../qunit/qunit-1.14.0'
	},
	shim : {
		'QUnit' : {
			exports : 'QUnit',
			init : function() {
				QUnit.config.autoload = false;
				QUnit.config.autostart = false;
			}
		}
	}
});

// require the unit tests.
require([ 'QUnit', 'ExpenseManagerTests' ], function(QUnit,
		expenseManagerTests) {
	// run the tests.
	expenseManagerTests.run();
	// start QUnit.
	QUnit.load();
	QUnit.start();
});

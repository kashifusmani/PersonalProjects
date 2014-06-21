$(document).ready(function() {
	// lettersonly rule: for fileds like firstname
	jQuery.validator.addMethod("lettersonly", function(value, element) {
		return this.optional(element) || /^[a-z]+$/i.test(value);
	}, "Letters only please"); 
	// lettersandnumonly: for fields like username/password
	jQuery.validator.addMethod("lettersandnumonly", function(value, element) {
		return this.optional(element) || /^[0-9A-Za-z]+$/i.test(value);
	}, "Letters and numbers only please");
	// atleastonedigit: needed in password
	jQuery.validator.addMethod("atleastonedigit", function(value, element) {
		return this.optional(element) || /^(?=.*\d)(?=.*[0-9a-zA-Z]).*$/i.test(value);
	}, "password should have atleast one digit");
	$("#registration_form").validate({
		rules: {
			firstname: {
				required:true,
				maxlength: 40,
				lettersonly: true
			},
			lastname: {
				required:true,
				maxlength: 40,
				lettersonly: true
			},
			email: {
				required: true,
				email: true,
				maxlength: 40
			},			
			password: {
				required:true,
				minlength: 8,
				maxlength: 20,
				lettersandnumonly: true,
				atleastonedigit:true
			}			
		},
	});
	$("#login_form").validate({
		rules: {
			email: {
				required: true,
				email: true,
				maxlength: 40
			},			
			password: {
				required:true
			}		
		},
	});
	$("#fileUploadForm").validate({
		rules: {
			filename: {
				required:true
			}			
		},
	});
});
			
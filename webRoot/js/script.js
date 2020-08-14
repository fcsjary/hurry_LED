$(document).ready(function(){
	
	$('#out').click(function(){
		var elem = $(this).closest('.item');
		$.confirm({
			'title'		: '',
			'message'	: '确认退出本系统?',
			'buttons'	: {
				'确定'	: {
					'class'	: 'blue',
					'action': function(){
					window.opener=null;
					var w = window.open("","_self"); 
					w.close();
					}
				},
				'取消'	: {
					'class'	: 'gray',
					'action': function(){}	// Nothing to do in this case. You can as well omit the action property.
				}
			}
		});
		
	});
	
});
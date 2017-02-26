$(document).ready(function () {
    var support = { transitions: Modernizr.csstransitions },
    // transition end event name
        transEndEventNames = { 'WebkitTransition': 'webkitTransitionEnd', 'MozTransition': 'transitionend', 'OTransition': 'oTransitionEnd', 'msTransition': 'MSTransitionEnd', 'transition': 'transitionend' },
        transEndEventName = transEndEventNames[ Modernizr.prefixed( 'transition' ) ],
        onEndTransition = function( el, callback ) {
            var onEndCallbackFn = function( ev ) {
                if( support.transitions ) {
                    if( ev.target != this ) return;
                    this.removeEventListener( transEndEventName, onEndCallbackFn );
                }
                if( callback && typeof callback === 'function' ) { callback.call(this); }
            };
            if( support.transitions ) {
                el.addEventListener( transEndEventName, onEndCallbackFn );
            }
            else {
                onEndCallbackFn();
            }
        };

    new GridFx(document.querySelector('.grid'), {
        imgPosition : {
            x : -0.5,
            y : 1
        }
    }); // grid fx
});
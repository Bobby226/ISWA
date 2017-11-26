
/*ANIMATE BG*/

var video = document.getElementById("bgvid"),
    pauseButton = document.querySelector("#pausevid button");

if (window.matchMedia('(prefers-reduced-motion)').matches)
{
    vid.removeAttribute("autoplay");
    vid.pause();
    pauseButton.innerHTML = "Paused";
}

function vidFade()
{
    video.classList.add("stopfade");
}
	      
video.addEventListener('ended', function()
		       {
			   // only functional if "loop" is removed 
			   video.pause();
			   vidFade();
		       }, false); 
pauseButton.addEventListener("click", function()
			     {
				 video.classList.toggle("stopfade");
				 if (video.paused)
				 {
				     video.play();
				     pauseButton.innerHTML = "Pause";
				 } else
				 {
				     video.pause();
				     pauseButton.innerHTML = "Paused";
				 }
			     }, false);

video.addEventListener('touchstart', function(e)
		       {
			   e.preventDefault();
			   video.play();
		       })
(function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function()
			 {(i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
			 m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
			})(window,document,'script','http://google-analytics.com/analytics.js','ga');
ga('create', 'UA-9896842-2', 'auto');
ga('send', 'pageview');

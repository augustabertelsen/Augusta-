/* jshint esversion: 6 */

document.getElementById('searchbutton').onclick = () => {
    fetch("/search?q=" + document.getElementById('searchbox').value)
    .then((response) => response.json())
    .then((data) => {
        let noResultsMessage = "No web page contains the query word.";
        let resultsMessage = data.length + " websites retrieved";
        let messageToUse = data.length === 0 ? noResultsMessage : resultsMessage;
        document.getElementById("responsesize").innerHTML = "<p>" + messageToUse + "</p>";

        let results = data.map((page) => `<li><a href="${page.url}">${page.title}</a></li>`).join("\n");
        document.getElementById("urllist").innerHTML = `<ul>${results}</ul>`;
    });
};
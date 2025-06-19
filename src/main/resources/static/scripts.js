const addButton = document.getElementById('event-add');
addButton.addEventListener('click', function () {
    const popupMenu = document.getElementById('popupMenu');
    popupMenu.classList.toggle('active');
});

const inputElement = document.getElementById('event-time');

inputElement.addEventListener('input', function () {
    const selectedDate = new Date(this.value);
    const currentDate = new Date();

    if (selectedDate <= currentDate) {
        this.setCustomValidity('Das Datum muss in der Zukunft liegen.');
    } else {
        this.setCustomValidity('');
    }
});

$(document).ready(function () {
    $('#event-description').on('input', function () {
        const maxLength = 50;
        const text = $(this).val();

        if (text.length > maxLength) {
            $(this).val(text.substring(0, maxLength));
        }
    });
});

$(document).ready(function () {
    $('#event-name').on('input', function () {
        const maxLength = 30;
        const text = $(this).val();

        if (text.length > maxLength) {
            $(this).val(text.substring(0, maxLength));
        }
    });
});

// Handle upvote button click
$('.upvote-Buttons').click(function () {
    const eventId = $(this).data('event-id');
    const downvoteId = '#event-downvote-' + eventId;
    const upvoteId = '#event-upvote-' + eventId;
    const votes = loadVotes();
    const oldUpVote = votes[eventId - 1].upvote;
    const oldDownVote = votes[eventId - 1].downvote;
    const newUpVote = $(upvoteId).text();
    const newDownVote = $(downvoteId).text();
    const donwvoteDiff = newDownVote - oldDownVote;
    const upvoteDiff = newUpVote - oldUpVote;
    if (upvoteDiff === 1) {
        const downvote = parseInt(newDownVote);
        const upvote = parseInt(newUpVote) - 1;
        $(upvoteId).text(upvote);
        $.ajax({
            type: 'POST',
            url: 'http://localhost:8080/updateVotes',
            data: {eventId: eventId, upvote: upvote, downvote: downvote}
        });
    } else {
        if (donwvoteDiff === 1) {
            const downvote = parseInt(newDownVote) - 1;
            const upvote = parseInt(newUpVote) + 1;
            $(downvoteId).text(downvote);
            $(upvoteId).text(upvote);
            $.ajax({
                type: 'POST',
                url: 'http://localhost:8080/updateVotes',
                data: {eventId: eventId, upvote: upvote, downvote: downvote}
            });
        } else {
            const downvote = parseInt(newDownVote);
            const upvote = parseInt(newUpVote) + 1;
            $(upvoteId).text(upvote);
            $.ajax({
                type: 'POST',
                url: 'http://localhost:8080/updateVotes',
                data: {eventId: eventId, upvote: upvote, downvote: downvote}
            });
        }
    }

});


// Handle downvote button click
$('.downVote-Buttons').click(function () {
    const eventId = $(this).data('event-id');
    const downvoteId = '#event-downvote-' + eventId;
    const upvoteId = '#event-upvote-' + eventId;
    const votes = loadVotes();
    const oldUpVote = votes[eventId - 1].upvote;
    const oldDownVote = votes[eventId - 1].downvote;
    const newUpVote = $(upvoteId).text();
    const newDownVote = $(downvoteId).text();

    const donwvoteDiff = newDownVote - oldDownVote;
    const upvoteDiff = newUpVote - oldUpVote;


    if (donwvoteDiff === 1) {
        const downvote = parseInt(newDownVote) - 1;
        const upvote = parseInt(newUpVote);
        $(downvoteId).text(downvote);
        $.ajax({
            type: 'POST',
            url: 'http://localhost:8080/updateVotes',
            data: {eventId: eventId, upvote: upvote, downvote: downvote}
        });
    } else {
        if (upvoteDiff === 1) {
            const downvote = parseInt(newDownVote) + 1;
            const upvote = parseInt(newUpVote) - 1;
            $(downvoteId).text(downvote);
            $(upvoteId).text(upvote);
            $.ajax({
                type: 'POST',
                url: 'http://localhost:8080/updateVotes',
                data: {eventId: eventId, upvote: upvote, downvote: downvote}
            });
        } else {
            const downvote = parseInt(newDownVote) + 1;
            const upvote = parseInt(newUpVote);
            $(downvoteId).text(downvote);
            $.ajax({
                type: 'POST',
                url: 'http://localhost:8080/updateVotes',
                data: {eventId: eventId, upvote: upvote, downvote: downvote}
            });
        }
    }
});


$('#show-modal').click(function () {
    $('#event-modal').toggle("hidden");
});

function loadVotes() {
    let votes = JSON.parse(sessionStorage.getItem('votes'));
    if (!votes) {
        votes = [];
        fetch('http://localhost:8080/allEvents').then(response => response.json()).then(events => {
            events.forEach(event => {
                votes.push({'id': event.id, 'upvote': event.upvote, 'downvote': event.downvote})
            });
            votes = JSON.stringify(votes);
            sessionStorage.setItem('votes', votes);
        });
    }
    return votes;

}

$(document).ready(loadVotes());







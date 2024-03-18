import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

function AddFilm() {
    const navigate = useNavigate();
    const [title, setTitle] = useState('');
    const [description, setDescription] = useState('');
    const [releaseYear, setReleaseYear] = useState('');
    const [rentalDuration, setRentalDuration] = useState(''); // State for rental duration
    const [selectedLanguageId, setSelectedLanguageId] = useState('');
    const [languages, setLanguages] = useState([]);
    const [actors, setActors] = useState([]);
    const [selectedActorIDs, setSelectedActorIDs] = useState([]);
    const [showActorInput, setShowActorInput] = useState(false);
    const [actorFilter, setActorFilter] = useState('');

    useEffect(() => {
        const fetchLanguagesAndActors = async () => {
            const responses = await Promise.all([
                axios.get('http://localhost:8080/home/allLanguages'),
                axios.get('http://localhost:8080/home/allActors')
            ]);
            setLanguages(responses[0].data);
            setActors(responses[1].data);
        };
        fetchLanguagesAndActors();
    }, []);

    const handleActorSelect = (actorID) => {
        if (!selectedActorIDs.includes(actorID)) {
            setSelectedActorIDs(prevIDs => [...prevIDs, actorID]);
            setShowActorInput(false);
            setActorFilter('');
        }
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        const payload = {
            title,
            description,
            releaseYear: parseInt(releaseYear, 10),
            rentalDuration: parseInt(rentalDuration, 10), // Include rental duration in the payload
            languageID: parseInt(selectedLanguageId, 10),
            actors: selectedActorIDs
        };

        console.log("Submitting payload:", JSON.stringify(payload, null, 2));

        try {
            await axios.post('http://localhost:8080/home/addFilm', payload);
            navigate('/home');
        } catch (error) {
            console.error('Failed to add film:', error);
        }
    };


    return (
        <form onSubmit={handleSubmit}>
            <div>
                <label>Title:</label>
                <input type="text" value={title} onChange={(e) => setTitle(e.target.value)} />
            </div>
            <div>
                <label>Description:</label>
                <input type="text" value={description} onChange={(e) => setDescription(e.target.value)} />
            </div>
            <div>
                <label>Release Year:</label>
                <input type="number" value={releaseYear} onChange={(e) => setReleaseYear(e.target.value)} />
            </div>
            <div>
                <label>Rental Duration:</label>
                <input type="number" value={rentalDuration} onChange={(e) => setRentalDuration(e.target.value)} />
            </div>
            <div>
                <label>Language:</label>
                <select value={selectedLanguageId} onChange={(e) => setSelectedLanguageId(e.target.value)}>
                    <option value="">Select a language</option>
                    {languages.map((language) => (
                        <option key={language.languageId} value={language.languageId}>
                            {language.name}
                        </option>
                    ))}
                </select>
            </div>
            <div>
                <button type="button" onClick={() => setShowActorInput(!showActorInput)}>Add Actor</button>
                {showActorInput && (
                    <div>
                        <input
                            type="text"
                            placeholder="Type to search actors"
                            value={actorFilter}
                            onChange={(e) => setActorFilter(e.target.value)}
                        />
                        <ul style={{ maxHeight: '200px', overflowY: 'auto', listStyleType: "none", padding: 0 }}>
                            {actors.filter(actor => `${actor.firstName} ${actor.lastName}`.toLowerCase().includes(actorFilter.toLowerCase()))
                                .map(actor => (
                                    <li key={actor.actorID} onClick={() => handleActorSelect(actor.actorID)} style={{ cursor: "pointer", padding: '5px' }}>
                                        {actor.firstName} {actor.lastName}
                                    </li>
                                ))}
                        </ul>
                    </div>
                )}
            </div>
            {selectedActorIDs.length > 0 && (
                <div>
                    Selected Actors:
                    <ul>
                        {selectedActorIDs.map((id) => {
                            const actor = actors.find(actor => actor.actorID === id);
                            return <li key={id}>{actor?.firstName} {actor?.lastName}</li>;
                        })}
                    </ul>
                </div>
            )}
            <button type="submit">Add Film</button>
        </form>
    );
}

export default AddFilm;
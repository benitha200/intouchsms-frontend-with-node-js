import React from 'react'
import { useEffect, useState } from 'react';
import { API_URL } from '../../../../../Constants/Index';

const MessageLogSummary = ({ token }) => {

    var myHeaders = new Headers();
    myHeaders.append("Authorization", "Token be37cf1708e91bf362af4355cad52c842d2c5919");

    var formdata = new FormData();
    formdata.append("start", "134394305");
    formdata.append("limit", "174384938");

    var requestOptions = {
        method: 'POST',
        headers: {
            'Authorization':`Token ${token}`
        },
        body: formdata,
        redirect: 'follow'
    };

    useEffect(() => {
        fetch(API_URL + "appgetmessagelogsummary", requestOptions)
            .then(response => response.json())
            .then(result => console.log(convertToJsonList(result)))
            .catch(error => console.log('error', error));
    })


    function convertToJsonList(response){
        let json_string = JSON.stringify(response[0])
        let list = json_string.replace('success','"success"')

        console.log(list)
        JSON.parse(list)
    }




    return (
        <div>MessageLogSummary</div>
    )
}

export default MessageLogSummary
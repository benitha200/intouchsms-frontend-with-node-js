// import { MdAddCircle } from 'react-icons/md'
import './Contacts.css'
import '../AddressBook.css'
import { Button, Col, Form, Row } from 'react-bootstrap'
import { useState } from 'react'
import Modal from 'react-bootstrap/Modal'
// import { json } from 'react-router-dom'
import { BsBook, BsEraserFill, BsFileArrowUpFill, BsPenFill, BsFillArrowUpRightSquareFill, BsPlusSquare, BsPersonBadgeFill } from 'react-icons/bs'
// import axios from 'axios'
import { Paper } from '@mui/material'
import { API_URL } from '../../../../Constants/Index'

const Contacts = ({ token }) => {

    // modal to create new

    const [show, setShow] = useState(false)
    const handleClose = () => setShow(false)
    const handleShow = () => setShow(true)

    // to view contacts

    const [view, setView] = useState(false)
    const openView = () => setView(false)
    const closeView = () => setView(true)



    // form

    const [name, setName] = useState()
    const [phonenumber, setPhonenumber] = useState()
    const [organisation, setOrganisation] = useState()
    const [email, setEmail] = useState()
    const [address, setAddress] = useState()

    const [responsemessage, setResponsemessage] = useState()
    const [responseContacts, setResponseContacts] = useState()

    function add_contact(e) {
        e.preventDefault();

        var formdata = new FormData();
        formdata.append("phone_number", phonenumber);
        formdata.append("name", name);
        formdata.append("organization", organisation);
        formdata.append("email", email);
        formdata.append("address", address);

        var requestOptions = {
            method: 'POST',
            headers: {
                'Authorization': `Token ${token}`
            },
            body: formdata,
            redirect: 'follow'
        };

        fetch(API_URL + "appaddcontact", requestOptions)
            .then(response => response.json())
            .then(result => setResponsemessage(convertToJson(result)))
            .catch(error => console.log('error', error));
    }

    function convertToJson(response) {
        let json_string = JSON.stringify(response[0]).replace("success", "'success'");
        json_string = json_string.replace("msg", "'msg'");
        const data = json_string.replace(/'/g, '"');
        const newstr = data.substring(1, data.length - 1)
        console.log(newstr);
        return JSON.parse(newstr);

    }

    // for getting valid json list
    function convertToJsonList(response) {
        let json_string = JSON.stringify(response[0]).replace('success', '"success"');
        // json_string = json_string.replace("msg", "'msg'");
        let data = json_string.startsWith("'");
        data = json_string.endsWith("'");
        data = json_string.substring(1, json_string.length - 1);
        data = data.replace(/\\/g, '');
        console.log(data);
        return JSON.parse(data)
    }

    // //Define the request options including the HTTP method, headers, and data payload
    // var requestOptions = {
    //     method: 'post',
    //     url: 'http://127.0.0.1:8000/api/appgetallcontacts',
    //     headers: {
    //         Authorization: `Token ${token}` // Include an authorization token in the request headers
    //     }
    // };

    // // Use the Axios library to send a POST request to the specified URL with the defined request options
    // axios(requestOptions)
    //     // When the response is received, access the data property which contains the response data as a JSON object
    //     // .then(response => setResponseContacts(convertToJsonList(response.data)))
    //     .then(response => console.log(convertToJsonList(response.data)))
    //     // If an error occurs during the Axios request, log it to the console
    //     .catch(error => console.log('error', error));



    var requestOptions = {
        method: 'POST',
        headers: {
            'Authorization': `Token ${token}`
        },
        redirect: 'follow'
    };

    fetch("http://127.0.0.1:8000/api/appgetallcontacts", requestOptions)
        .then(response => response.json())
        .then(result => setResponseContacts(convertToJsonList(result)))
        // .then(result=>console.log(convertToJsonList(result)))
        .catch(error => console.log('error', error));

    // console.log(responseContacts.response)



    // table


    return (
        <div className='d-flex flex-row gap-3'>

            {/* Add Modal */}

            <Modal show={show} onHide={handleClose} className="modal">
                <Modal.Header className='modal-header' closeButton>
                    <span className='contact-modal-title'><BsBook /> ADD CONTACTS</span>
                </Modal.Header>
                <form encType='multipart/form-data' onSubmit={add_contact}>
                    <Modal.Body>

                        <Row>
                            <Col>
                                <Form.Label>Name</Form.Label>
                                <Form.Control
                                    type='text'
                                    onChange={(e) => setName(e.target.value)}
                                    value={name}
                                    required />
                            </Col>
                            <Col>
                                <Form.Label>Mobile Phone</Form.Label>
                                <Form.Control
                                    type='text'
                                    onChange={(e) => setPhonenumber(e.target.value)}
                                    value={phonenumber}
                                    required
                                />
                            </Col>
                        </Row>
                        <Row>
                            <Col>
                                <Form.Label>Email</Form.Label>
                                <Form.Control
                                    type='text'
                                    onChange={(e) => setEmail(e.target.value)}
                                    value={email}
                                    required
                                />
                            </Col>
                            <Col>
                                <Form.Label>Organisation</Form.Label>
                                <Form.Control
                                    type='text'
                                    onChange={(e) => setOrganisation(e.target.value)}
                                    value={organisation}
                                    required

                                />
                            </Col>
                        </Row>
                        <Row>
                            <Col>
                                <Form.Label>Address</Form.Label>
                                <Form.Control
                                    type='text'
                                    onChange={(e) => setAddress(e.target.value)}
                                    value={address}
                                    required
                                />
                            </Col>

                        </Row>

                        {responsemessage && (<span style={{ color: "tomato" }}><b><i>*{responsemessage.msg}</i></b></span>)}


                    </Modal.Body>
                    <Modal.Footer>
                        <Row>
                            <Col>
                                <Button className='btn btn-dark opacity-50' type='submit' block>Save&Close</Button>
                            </Col>
                            <Col>
                                <Button className='btn btn-dark opacity-50'> Save&New</Button>
                            </Col>
                            <Col>
                                <Button className='btn btn-dark opacity-50' onClick={handleClose}> Cancel</Button>
                            </Col>

                        </Row>
                    </Modal.Footer>
                </form>

            </Modal>

            {/* end modal */}

            {/* View Modal */}

            <Modal show={show} onHide={handleClose} className="modal">
                <Modal.Header className='modal-header' closeButton>
                    <span className='contact-modal-title'><BsBook /> VIEW CONTACTS</span>
                </Modal.Header>
                <form encType='multipart/form-data' onSubmit={add_contact}>
                    <Modal.Body>

                        <Row>
                            <Col>
                                <Form.Label>Name</Form.Label>
                                <Form.Control
                                    type='text'
                                    onChange={(e) => setName(e.target.value)}
                                    value={name}
                                    required />
                            </Col>
                            <Col>
                                <Form.Label>Mobile Phone</Form.Label>
                                <Form.Control
                                    type='text'
                                    onChange={(e) => setPhonenumber(e.target.value)}
                                    value={phonenumber}
                                    required
                                />
                            </Col>
                        </Row>
                        <Row>
                            <Col>
                                <Form.Label>Email</Form.Label>
                                <Form.Control
                                    type='text'
                                    onChange={(e) => setEmail(e.target.value)}
                                    value={email}
                                    required
                                />
                            </Col>
                            <Col>
                                <Form.Label>Organisation</Form.Label>
                                <Form.Control
                                    type='text'
                                    onChange={(e) => setOrganisation(e.target.value)}
                                    value={organisation}
                                    required

                                />
                            </Col>
                        </Row>
                        <Row>
                            <Col>
                                <Form.Label>Address</Form.Label>
                                <Form.Control
                                    type='text'
                                    onChange={(e) => setAddress(e.target.value)}
                                    value={address}
                                    required
                                />
                            </Col>

                        </Row>

                        {responsemessage && (<span style={{ color: "tomato" }}><b><i>*{responsemessage.msg}</i></b></span>)}


                    </Modal.Body>
                    <Modal.Footer>
                        <Row>
                            <Col>
                                <Button className='btn btn-dark opacity-50' type='submit' block>Save&Close</Button>
                            </Col>
                            <Col>
                                <Button className='btn btn-dark opacity-50'> Save&New</Button>
                            </Col>
                            <Col>
                                <Button className='btn btn-dark opacity-50' onClick={handleClose}> Cancel</Button>
                            </Col>

                        </Row>
                    </Modal.Footer>
                </form>

            </Modal>

            {/* end modal */}
            <Paper sx={{marginTop:'0.5rem', height:'100%'}}>
                <span className="text-bold p-4 pt-4"><BsPersonBadgeFill/> My Contacts</span>
                <hr />
                <div>
                    <div className="btn-group contact-table-headers ">
                        <Button type="button" className='contact-buttons text-dark bg-transparent p-2 m-0 ml-1 btn btn-light' onClick={handleShow}><BsPlusSquare /> Add</Button>
                        <button type="button" className='contact-buttons text-dark bg-transparent'><BsPenFill /> View&Modify</button>
                        <button type="button" className='contact-buttons text-dark bg-transparent'><BsFileArrowUpFill /> Import..Contacts</button>
                        <button type="button" className='contact-buttons text-dark bg-transparent'><BsFillArrowUpRightSquareFill /> Refresh</button>
                        <button type="button" className='contact-buttons text-dark bg-transparent'><BsEraserFill /> Remove</button>
                        <input type="search" placeholder='Search...' className='form-control' /><button type="button" className='app-buttons'>Search</button>

                    </div>

                    <div className='table pt-4'>
                        <table className='table table-hover w-100 pt-0' style={{ height: '100%', fontFamily: 'verdana', fontWeight: 'bold' }}>
                            <thead>
                                <tr>
                                    <th>Name</th>
                                    <th>Organisation</th>
                                    <th>Phone Number</th>
                                </tr>
                            </thead>
                            <tbody>

                                {responseContacts && responseContacts.response.map((contact) => (
                                    <tr key={contact.pk} onClick={openView}>
                                        <td className="table-data">{contact.fields.name}</td>
                                        <td className="table-data">{contact.fields.organisation}</td>
                                        <td className="table-data">{contact.fields.phoneno}</td>
                                    </tr>
                                ))}

                            </tbody>

                        </table>
                    </div>
                </div>
            </Paper>

            <div className='vr'>

            </div>
            <div className='pt-4 pl-2'>
                <div className='btn-group'>
                    <button type="button" className='contact-buttons'>Add</button>
                </div>
                <div className='table-responsive-sm'>
                    <table className='table'>
                        <thead>
                            <tr>
                                <th>Contact Type</th>
                                <th>Contact</th>
                                <th>Main</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <td>Shema</td>
                                <td>ISCO</td>
                                <td>0785456565</td>
                            </tr>
                        </tbody>

                    </table>
                </div>
            </div>
        </div>
    )
}

export default Contacts
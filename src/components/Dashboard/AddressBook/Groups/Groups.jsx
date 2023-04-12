import React from 'react'
import { MdAddCircle } from 'react-icons/md'
import './Groups.css'
import '../AddressBook.css'
import { Button, Col, Form, FormGroup, InputGroup, Row } from 'react-bootstrap'
import { useState } from 'react'
import Modal from 'react-bootstrap/Modal'
import { Input } from 'reactstrap'
import { BsInfoCircleFill, BsPeopleFill, BsPlusCircleFill } from 'react-icons/bs'
import { BsBook, BsEject, BsEraser, BsEraserFill, BsFileArrowUpFill, BsPenFill, BsFillArrowUpRightSquareFill, BsPlusSquare } from 'react-icons/bs'
import Popup from 'reactjs-popup'
import axios from 'axios'
import { Paper } from '@material-ui/core'
import { API_URL } from '../../../../Constants/Index'


const Groups = ({ token }) => {

  const [show, setShow] = useState(false)
  const handleClose = () => setShow(false)
  const handleShow = () => setShow(true)

  // add group 
  const [name, setName] = useState();
  const [description, setDescription] = useState();
  const [message, setMessage] = useState();

  // to view contacts

  const [view, setView] = useState(false)
  const openView = () => setView(true)
  const closeView = () => setView(false)

  // get groups
  const [groupList, setGroupList] = useState();

  const add_group = (e) => {
    e.preventDefault();
    // var myHeaders = new Headers();
    // myHeaders.append("Authorization", "Token 63c23962113c27a9418a7069b4f6f022670f9913");

    var formdata = new FormData();
    formdata.append("name", name);
    formdata.append("description", description);

    var requestOptions = {
      method: 'POST',
      mode: 'cors',
      headers: {
        Authorization: `Token ${token}`
      },
      body: formdata,
      redirect: 'follow'
    };

    fetch(API_URL + "appaddgroup", requestOptions)
      .then(response => response.json())
      .then(result => setMessage(convertToJson(result)))
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

  // Define the request options including the HTTP method, headers, and data payload
  var requestOptions = {
    method: 'post',
    url: 'http://127.0.0.1:8000/api/appgetallgroups',
    headers: {
      Authorization: `Token ${token}` // Include an authorization token in the request headers
    }
  };

  // Use the Axios library to send a POST request to the specified URL with the defined request options
  axios(requestOptions)
    // When the response is received, access the data property which contains the response data as a JSON object
    .then(response => setGroupList(convertToJsonList(response.data)))
    // If an error occurs during the Axios request, log it to the console
    .catch(error => console.log('error', error));


  // var requestOptions = {
  //   method: 'POST',
  //   headers: {
  //     Authorization: `Token ${token}`
  //   },
  //   redirect: 'follow'
  // };

  // fetch("http://127.0.0.1:8000/api/appgetallgroups", requestOptions)
  //   .then(response => response.json())
  //   .then(result => setGroupList(convertToJsonList(result)))
  //   .catch(error => console.log('error', error));




  // for getting valid json list
  function convertToJsonList(response) {
    let json_string = JSON.stringify(response[0]).replace('success', '"success"');
    // json_string = json_string.replace("msg", "'msg'");
    let data = json_string.startsWith("'");
    data = json_string.endsWith("'");
    data = json_string.substring(2, json_string.length - 2);
    data = data.replace(/\\/g, '');
    console.log(data);
    return JSON.parse(data)
  }

  function edit_group() {

    var formdata = new FormData();
    formdata.append("pk", "249");
    formdata.append("name", "ABAHUZA");
    formdata.append("description", "none");

    var requestOptions = {
      method: 'POST',
      headers: {
        'Authorization': `Token ${token}`
      },
      body: formdata,
      redirect: 'follow'
    };

    fetch("http://127.0.0.1:8000/api/appeditgroup", requestOptions)
      .then(response => response.text())
      .then(result => console.log(result))
      .catch(error => console.log('error', error));

  }


  return (
    <div className='d-flex flex-row gap-3'>


      {/* Add Modal */}

      <Modal show={show} onHide={handleClose} className="modal">
        <form encType='multipart/form-data' onSubmit={add_group}>
          <Modal.Header className='modal-header' closeButton>
            <span className='contact-modal-title'><BsPlusCircleFill /> ADD GROUP</span>
          </Modal.Header>
          <Modal.Body>

            <FormGroup className="m-2">
              <InputGroup>
                <InputGroup.Text>
                  <BsPeopleFill />
                </InputGroup.Text>
                <Input
                  type='description'
                  className="form-control"
                  placeholder='Name'
                  name='name'
                  // onChange={(e)=>handle(e)}
                  onChange={(e) => setName(e.target.value)}
                  value={name}
                />
              </InputGroup>

            </FormGroup>
            <FormGroup className='m-2'>
              <InputGroup>
                <InputGroup.Text>
                  <BsInfoCircleFill />
                </InputGroup.Text>
                <Input
                  type='description'
                  className="form-control"
                  placeholder='Description'
                  name='description'
                  // onChange={(e)=>handle(e)}
                  onChange={(e) => setDescription(e.target.value)}
                  value={description}
                />
              </InputGroup>

            </FormGroup>

            {message && (<span><b><i>{message.msg}</i></b></span>)}


          </Modal.Body>
          <Modal.Footer>
            <Row>
              <Col>


                <button className='btn btn-dark opacity-50' type='submit'>Save&Close</button>


              </Col>
              <Col>
                <Button className='btn btn-dark opacity-50' onClick={handleClose}> Cancel</Button>
              </Col>

            </Row>
          </Modal.Footer>
        </form>

      </Modal>

      {/* Add modal */}



      <Paper sx={{marginTop:'0.5rem', height:'100%'}}>
        <div className='p-2'>
        <span className="text-bold p-2"><BsPeopleFill/>  My Groups </span>
        <hr/>
          <div className="btn-group groups-table-headers pt-3 ">
            <Button type="button" className='contact-buttons text-dark bg-transparent p-2 m-0 ml-1' onClick={handleShow}><BsPlusSquare /> Add</Button>
            <button type="button" className='contact-buttons text-dark bg-transparent'><BsPenFill /> View&Modify</button>
            <button type="button" className='contact-buttons text-dark bg-transparent'><BsFileArrowUpFill /> Import..Contacts</button>
            <button type="button" className='contact-buttons text-dark bg-transparent'><BsFillArrowUpRightSquareFill /> Refresh</button>
            <button type="button" className='contact-buttons text-dark bg-transparent'><BsEraserFill /> Remove</button>
            <input type="search" placeholder='Search...' className='form-control' /><button type="button" className='app-buttons'>Search</button>

          </div>

          <div className='table-responsive-sm pt-4'>
            <table className='table table-hover w-100 pt-0' style={{ height: '100%', fontFamily: 'verdana', fontWeight: 'bold' }}>
              <thead>
                <tr>
                  <th>Name</th>
                  <th>Members</th>
                </tr>
              </thead>
              <tbody>


                {groupList && groupList.response.map((group) => (

                  <tr key={group.pk} onClick={openView}>
                    {/* View Modal */}

                    <Modal show={view} onHide={closeView} className="modal">
                      <form encType='multipart/form-data' onSubmit={edit_group}>
                        <Modal.Header className='modal-header' closeButton>
                          <span className='contact-modal-title'><BsPlusCircleFill /> VIEW & EDIT GROUP</span>
                        </Modal.Header>
                        <Modal.Body>

                          <FormGroup className="m-2">
                            <InputGroup>
                              <InputGroup.Text>
                                <BsPeopleFill />
                              </InputGroup.Text>
                              <Input
                                type='text'
                                className="form-control"
                                // placeholder='Name'
                                name='name'
                                // onChange={(e)=>handle(e)}
                                // onChange={(e) => setPk(e.target.value)}
                                value={group.pk}
                                readOnly
                              />
                            </InputGroup>

                          </FormGroup>

                          <FormGroup className="m-2">
                            <InputGroup>
                              <InputGroup.Text>
                                <BsPeopleFill />
                              </InputGroup.Text>
                              <Input
                                type='text'
                                className="form-control"
                                placeholder='Name'
                                name='name'
                                // onChange={(e)=>handle(e)}
                                onChange={(e) => setName(e.target.value)}
                                value={group.fields.name}
                              />
                            </InputGroup>

                          </FormGroup>
                          <FormGroup className='m-2'>
                            <InputGroup>
                              <InputGroup.Text>
                                <BsInfoCircleFill />
                              </InputGroup.Text>
                              <Input
                                type='description'
                                className="form-control"
                                placeholder='Description'
                                name='description'
                                // onChange={(e)=>handle(e)}
                                onChange={(e) => setDescription(e.target.value)}
                                value={group.fields.desc}
                              />
                            </InputGroup>

                          </FormGroup>

                          {message && (<span><b><i>{message.msg}</i></b></span>)}


                        </Modal.Body>
                        <Modal.Footer>
                          <Row>
                            <Col>


                              <button className='btn btn-dark opacity-50' type='submit'>Save&Close</button>


                            </Col>
                            <Col>
                              <Button className='btn btn-dark opacity-50' onClick={closeView}> Cancel</Button>
                            </Col>

                          </Row>
                        </Modal.Footer>
                      </form>

                    </Modal>

                    {/* end View Modal */}
                    <td className='table-data'>{group.fields.name}</td>
                    <td className='table-data'>{group.fields.members}</td>
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

export default Groups
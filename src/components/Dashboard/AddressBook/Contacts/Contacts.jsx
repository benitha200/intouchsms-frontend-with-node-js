import React from 'react'
import { MdAddCircle } from 'react-icons/md'
import './Contacts.css'
import '../AddressBook.css'
import { Button, Col, Form, FormGroup, InputGroup, Row } from 'react-bootstrap'
import { useState } from 'react'
import Modal  from 'react-bootstrap/Modal'
const Contacts = () => {

    const [show,setShow] = useState(false)

    const handleClose = () => setShow(false)
    const handleShow = () =>setShow(true)

  return (
    <div className='d-flex flex-row gap-3'>

        {/* Add Modal */}

        <Modal show={show} onHide={handleClose} className="modal">
            <Modal.Header className='modal-header' closeButton>
                <span className='contact-modal-title'><i className=''></i> <i class="fa fa-address-book" aria-hidden="true"></i> ADD CONTACTS</span>
            </Modal.Header>
            <Modal.Body>
                <Form>
                    <Row>
                        <Col>
                        <Form.Label>Name</Form.Label>
                        <Form.Control type='text' required></Form.Control>
                        </Col>
                        <Col>
                        <Form.Label>Mobile Phone</Form.Label>
                        <Form.Control type='text'></Form.Control>
                        </Col>
                    </Row>
                    <Row>
                        <Col>
                        <Form.Label>Email</Form.Label>
                        <Form.Control type='text'></Form.Control>
                        </Col>
                        <Col>
                        <Form.Label>Organisation</Form.Label>
                        <Form.Control type='text'></Form.Control>
                        </Col>
                    </Row>
                    <Row>
                        <Col>
                        <Form.Label>Address</Form.Label>
                        <Form.Control type='text'></Form.Control>
                        </Col>
                       
                    </Row>
                 
                </Form>
            </Modal.Body>
            <Modal.Footer>
            <Row>
                        <Col>
                        <Button className='btn btn-dark opacity-50' onClick={handleClose}>Save&Close</Button>
                        </Col>
                        <Col>
                        <Button className='btn btn-dark opacity-50'> Save&New</Button>
                        </Col>
                        <Col>
                        <Button className='btn btn-dark opacity-50'> Cancel</Button>
                        </Col>
                        
                    </Row>
            </Modal.Footer>

        </Modal>


        {/* end modal */}
        <div>    
            <div className="btn-group pt-4">
                <Button variant='primary' type="button" className='contact-buttons btn btn-dark p-2 m-0 ml-1' onClick={handleShow}>Add</Button>
                <button type="button" className='contact-buttons'>View&Modify</button>
                <button type="button" className='contact-buttons'>Import..Contacts</button>
                <button type="button" className='contact-buttons'>Refresh</button>
                <button type="button" className='contact-buttons'>Remove</button>
                <input type="search" placeholder='Search...' className='form-control'/><button type="button" className='app-buttons'>Search</button>
                
            </div>
            
            <div className='table-responsive-sm pt-4'>
                <table className='table'>
                    <thead>
                        <tr>
                            <th>Name</th>
                            <th>Organisation</th>
                            <th>Phone Number</th>
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
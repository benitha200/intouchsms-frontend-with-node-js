import React, { useEffect, useState } from 'react'
import './Header.css'
import { Stack } from '@mui/material'
import { API_URL, TOKEN } from '../../../Constants/Index'
import axios from 'axios'
import { useNavigate } from 'react-router-dom'
// import { Button } from '@mui/material';
import MenuIcon from '@mui/icons-material/Menu';
import { useProSidebar } from 'react-pro-sidebar';
import Modal from 'react-bootstrap/Modal';
import { Button, Col, Form, FormGroup, InputGroup, Row, DropdownButton, Dropdown, FormControl } from 'react-bootstrap';
import { BsArrowLeftRight, BsBagCheck, BsCash, BsCashCoin, BsEnvelopeFill, BsHouseDoorFill, BsInfo, BsInfoCircle, BsPersonCircle, BsPersonFill, BsPhoneLandscape, BsPhoneVibrate, BsSearch, BsTelephone } from 'react-icons/bs';



const data = [
    {
        "id": 1,
        "author": "J.K. Rowling",
        "title": "Harry Potter and the Philosopher's Stone",
        "release_date": "1997-06-26"
    },
    {
        "id": 2,
        "author": "George R.R. Martin",
        "title": "A Game of Thrones",
        "release_date": "1996-08-01"
    },
    {
        "id": 3,
        "author": "J.R.R. Tolkien",
        "title": "The Lord of the Rings",
        "release_date": "1954-07-29"
    }
]


const Header = ({ token }) => {

    //modal
    const [show, setShow] = useState(false)
    const handleClose = () => setShow(false)
    const handleShow = () => setShow(true)
    const { collapseSidebar } = useProSidebar()
    const navigate = useNavigate();
    const [showTable, setShowTable] = useState(false);
    const [selectedItem, setSelectedItem] = useState(null);

    const handleButtonClick = () => {
        // Toggle the visibility of the table
        setShowTable(!showTable);
    };

    const handleTableRowClick = (item) => {
        // Set the selected item and populate the form data
        setSelectedItem(item);
        // TODO: Populate form data here
    };

    const [profile, setProfile] = useState()
    const [balance, setBalance] = useState()
    const [publishedPackages, setPublishedPackages] = useState()
    const [selectedPackage, setSelectedPackage] = useState(null);
    const [logoutData, setLogoutData] = useState()

    let data1 = JSON.parse(JSON.stringify(data))


    // var requestOptions = {
    //     method: 'POST',
    //     mode: 'cors',
    //     headers: {
    //         'Authorization': `Token ${token}`
    //     }
    // };

    // fetch("http://127.0.0.1:8000/api/getmybalance", requestOptions)
    //     .then(response => response.text())
    //     .then(result => setBalance(result.slice(2, -2).slice(42, 49)))
    //     .catch(error => console.log('error', error));

    var requestOptions = {
        method: 'POST',
        mode: 'cors',
        headers: {
            'Authorization': `Token ${token}`
        }
    };



    useEffect(() => {
        fetch(API_URL +"getmyprofile", requestOptions)
            .then(response => response.json())
            .then(result => setProfile(convertToJson(result)))
            // .then(result => setProfile(result))
            .catch(error => console.log('error', error));
    });




    function convertToJson(response) {
        let json_string = JSON.stringify(response[0]).replace("success", "'success'");
        json_string = json_string.replace("response", "'response'");
        const data = json_string.replace(/'/g, '"');
        const newstr = data.substring(1, data.length - 1)
        console.log(newstr)
        return JSON.parse(newstr);

    }
    // useEffect(()=>{
    //     console.log(" My Balance"
    //      +balance)
    // })

    function logout() {
        var requestOptions = {
            method: 'POST',
            headers: {
                'Authorization': `Token ${token}`
            },
            redirect: 'follow'
        };

        fetch(API_URL +"logout", requestOptions)
            .then(response => response.text())
            .then(result => setLogoutData(result))
            .catch(error => console.log('error', error));

        console.log(logoutData)
        if (logoutData.includes("true")) {
            localStorage.setItem("refreshToken", "")
            navigate('/login', { replace: true });
            window.location.reload(true)

        }

    }


    // fetch packages

    var formdata = new FormData();
    formdata.append("start", "1453818286");
    formdata.append("limit", "1453818286");

    var requestOptions = {
        method: 'POST',
        headers: {
            'Authorization': `Token ${token}`
        },
        body: formdata,
        redirect: 'follow'
    };

    fetch("http://127.0.0.1:8000/api/getpublishedpackages", requestOptions)
        .then(response => response.json())
        .then(result => setPublishedPackages(JSON.parse(result)))
        .catch(error => console.log('error', error));

    // console.log(publishedPackages);



    return (
        <div className='header'>

            <div className='container'>

                <div className='app-title'>
                    <span className="app-main-header"> Intouchsms Communication Platform</span>
                    {/* <div className="burger-icon">
                        <Button onClick={() => collapseSidebar()} sx={{ color: 'black' }} className=''><MenuIcon /></Button>

                    </div> */}
                </div>
                <div className='pt-2'>


                    <Stack direction="row" gap={2}>


                        <button className='account-buttons' onClick={setShow}>Top Up</button>

                        {/* Add Modal */}

                        <Modal show={show} onHide={handleClose} className="modal">
                            <Modal.Header className='modal-header' closeButton>
                                <span className='contact-modal-title'><i className='fa fa-key'></i> TopUp Credit</span>
                            </Modal.Header>
                            <Form >
                                <Modal.Body>


                                    {/* <InputGroup className="mb-3">
                        <InputGroup.Text>
                          <BsSearch />
                        </InputGroup.Text>
                        <Form.Control placeholder="Search" aria-label="Search" />
                      </InputGroup> */}
                                    <span>Price Information</span>
                                    <hr />

                                    {/* <div className='d-flex flex-column gap-2 mb-3'>
                                        <InputGroup>
                                            
                                            <InputGroup.Text>
                                                <BsBagCheck />
                                            </InputGroup.Text>
                                            <Form.Control className='m-0 h-100' type='text' placeholder='Package' required></Form.Control>
                                        </InputGroup>
                                        <div>
                                            <InputGroup>
                                                <Button className='btn btn-dark opacity-50 m-0 h-100 w-100' onClick={handleButtonClick}>Select</Button>
                                            </InputGroup>

                                            {showTable && (
                                                <table border={1}>
                                                    <thead>
                                                        <tr>
                                                            <th>Column 1</th>
                                                            <th>Column 2</th>
                                                            <th>Column 3</th>
                                                        </tr>
                                                    </thead>
                                                    <tbody>
                                                        {data1 &&

                                                            data1.map((item) => (
                                                                <tr key={item.id} onClick={() => handleTableRowClick(item)}>
                                                                    <td>{item.author}</td>
                                                                    <td>{item.title}</td>
                                                                    <td>{item.release_date}</td>
                                                                </tr>
                                                            ))}
                                                    </tbody>
                                                </table>
                                            )}
                                            {/* TODO: Render the form here 
                                        </div>
                                    </div> */}



                                    <InputGroup>
                                        <DropdownButton as={InputGroup.Prepend} title="Select Package" className="m-0 bg-dark">
                                            {publishedPackages && publishedPackages.response.map((packagedetails) => (
                                                <Dropdown.Item key={packagedetails.pk} eventKey={packagedetails.pk}>
                                                    <div className="d-flex flex-row justify-content-xl-between">
                                                        <span className="p-2 border-right">{packagedetails.fields.name} </span> <span className="p-2 border-right"> min:{packagedetails.fields.minimumprice}</span><span className="p-2 border-right"> max: {packagedetails.fields.maximumprice} </span><span className="p-2"> Unit Price : {packagedetails.fields.unitpricetaxincl}</span>  
                                                    </div>
                                                    
                                                </Dropdown.Item>
                                               
                                            ))}
                                        </DropdownButton>
                                        <FormControl
                                            disabled={!selectedPackage}
                                            value={selectedPackage ? selectedPackage.fields.name : ''}
                                            placeholder="Release Date"
                                            onChange={() => { }}
                                        />
                                    </InputGroup>


                                    <div className='d-flex flex-row gap-2 mb-3'>
                                        <InputGroup>
                                            {/* <Form.Label>Credits Range</Form.Label> */}
                                            <InputGroup.Text>
                                                Credits Range (From)
                                            </InputGroup.Text>
                                            <Form.Control type='text' placeholder='0.0' required></Form.Control>
                                        </InputGroup>

                                        <InputGroup>
                                            <InputGroup.Text>
                                                {/* <BsCashCoin /> */}
                                                To:
                                            </InputGroup.Text>
                                            {/* <Form.Label>Confirm New password</Form.Label> */}
                                            <Form.Control type='textarea' placeholder='' required></Form.Control>
                                        </InputGroup>
                                    </div>


                                    <div className='d-flex flex-row gap-2 mb-3'>
                                        <InputGroup>
                                            {/* <Form.Label>Credits Range</Form.Label> */}
                                            <InputGroup.Text>
                                                Price Range (From)
                                            </InputGroup.Text>
                                            <Form.Control type='text' placeholder='0.0' required></Form.Control>
                                        </InputGroup>

                                        <InputGroup>
                                            <InputGroup.Text>
                                                {/* <BsCashCoin /> */}
                                                To:
                                            </InputGroup.Text>
                                            {/* <Form.Label>Confirm New password</Form.Label> */}
                                            <Form.Control type='textarea' placeholder='' required></Form.Control>
                                        </InputGroup>
                                    </div>


                                    <div className='d-flex flex-row gap-2 mb-3'>
                                        <InputGroup>
                                            {/* <Form.Label>New Password</Form.Label> */}
                                            <InputGroup.Text>
                                                {/* <BsCash /> */}
                                                Unit Price
                                            </InputGroup.Text>
                                            <Form.Control type='text' placeholder='' required></Form.Control>
                                        </InputGroup>

                                        <InputGroup>
                                            <InputGroup.Text>
                                                Quantity
                                            </InputGroup.Text>
                                            {/* <Form.Label>Confirm New password</Form.Label> */}
                                            <Form.Control type='textarea' placeholder='' required></Form.Control>
                                        </InputGroup>
                                    </div>

                                    <div className='d-flex flex-row gap-2 mb-3'>
                                        <InputGroup>
                                            {/* <Form.Label>New Password</Form.Label> */}
                                            <InputGroup.Text>
                                                {/* <BsCash /> */}
                                                Tot. Price Tax Excl.
                                            </InputGroup.Text>
                                            <Form.Control type='text' placeholder='' required></Form.Control>
                                        </InputGroup>

                                        <InputGroup>
                                            <InputGroup.Text>
                                                {/* <BsCashCoin /> */}
                                                VAT (18%)
                                            </InputGroup.Text>
                                            {/* <Form.Label>Confirm New password</Form.Label> */}
                                            <Form.Control type='text' placeholder='' required></Form.Control>
                                        </InputGroup>
                                    </div>

                                    <div className='d-flex flex-row gap-2 mb-3'>
                                        <InputGroup>
                                            {/* <Form.Label>New Password</Form.Label> */}
                                            <InputGroup.Text>
                                                {/* <BsCash /> */}
                                                Tot. Price Tax Excl.
                                            </InputGroup.Text>
                                            <Form.Control type='text' placeholder='' required></Form.Control>
                                        </InputGroup>

                                    </div>

                                    <span className="">Payment Information</span>
                                    <hr />

                                    <div className='d-flex flex-row gap-2 mb-3'>
                                        <InputGroup>
                                            {/* <Form.Label>New Password</Form.Label> */}
                                            <InputGroup.Text>
                                                <BsCashCoin />
                                            </InputGroup.Text>
                                            <Form.Control className='m-0 h-100' type='select' placeholder='Payment Method' required></Form.Control>
                                        </InputGroup>

                                        <InputGroup>
                                            {/* <Form.Label>New Password</Form.Label> */}
                                            <InputGroup.Text>
                                                <BsPhoneVibrate />
                                            </InputGroup.Text>
                                            <Form.Control type='text' placeholder='Phone Number' required></Form.Control>
                                        </InputGroup>
                                    </div>
                                    <div className='d-flex flex-row gap-2 mb-3'>
                                        <InputGroup>
                                            {/* <Form.Label>New Password</Form.Label> */}
                                            <InputGroup.Text>
                                                <BsCash />
                                            </InputGroup.Text>
                                            <Form.Control type='text' placeholder='Amount' required></Form.Control>
                                        </InputGroup>

                                    </div>

                                </Modal.Body>
                                <Modal.Footer>

                                    <Button className='btn btn-dark opacity-60' type='submit'>Submit</Button>
                                    <Button className='btn btn-dark opacity-60' onClick={handleClose}> Cancel</Button>

                                </Modal.Footer>
                            </Form>

                        </Modal>


                        {/* end modal */}


                        <button className='account-buttons' onClick={logout}>Logout</button>
                    </Stack>

                    <div className='account-info'>

                        <div>
                            {profile && (
                                <div>
                                    <span>Welcome <b>{profile.response.names}</b></span><br />
                                    <span>Balance: <b>{profile.response.creditsbalance} </b> Credits</span>
                                </div>
                            )}
                        </div>




                        {/* <span>Welcome {profile.response}</span> */}
                        <br />
                        {/* <span>Balance: {profile.response.creditsbalance.toLocaleString('en-US', { style: 'currency' })} Credits</span> */}
                    </div>






                </div>



            </div>

        </div>

    )
}

export default Header

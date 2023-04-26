import { useEffect, useState } from 'react'
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
import Select from 'react-select';


// const options = [
//     { value: 'chocolate', label: 'Chocolate' },
//     { value: 'strawberry', label: 'Strawberry' },
//     { value: 'vanilla', label: 'Vanilla' },
// ];

const Header = ({ token, setToken }) => {

    //modal
    const [show, setShow] = useState(false)
    const handleClose = () => setShow(false)
    const handleShow = () => setShow(true)
    const { collapseSidebar } = useProSidebar()
    const navigate = useNavigate();
    const [showTable, setShowTable] = useState(false);
    const [selectedItem, setSelectedItem] = useState(null);


    // const handleButtonClick = () => {
    //     // Toggle the visibility of the table
    //     setShowTable(!showTable);
    // };

    // const handleTableRowClick = (item) => {
    //     // Set the selected item and populate the form data
    //     setSelectedItem(item);
    //     // TODO: Populate form data here
    // };

    const [profile, setProfile] = useState()
    const [balance, setBalance] = useState()
    const [publishedPackages, setPublishedPackages] = useState()
    const [selectedPackage, setSelectedPackage] = useState(null);
    const [logoutData, setLogoutData] = useState();
    const [priceInfo, setPriceInfo] = useState();

    const [unitPrice, setUnitPrice] = useState();
    const [amount, setAmount] = useState(0);
    const [quantity, setQuantity] = useState();
    const [totPriceTaxEx, setTotPriceTaxEx] = useState();
    const [vat, setVat] = useState();
    const [totPriceTaxInc, setTotPriceTaxInc] = useState();




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
        fetch(API_URL + "getmyprofile", requestOptions)
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

        fetch(API_URL + "logout", requestOptions)
            .then(response => response.text())
            .then(result => {
                setLogoutData(result);
                localStorage.removeItem('token');
                setToken(null);
                navigate('/login', { replace: true });
            })
            .catch(error => console.log('error', error));

        // console.log(logoutData)
        // if (logoutData.includes("true")) {

        //     window.location.reload(true)

        // }

    }




    // console.log(publishedPackages);


    if (selectedPackage) {
        alert(selectedPackage);
    }

    function handleAmount(e) {


        setAmount(e.target.value)


        let uprice = priceInfo ? priceInfo.response.unitprice : 0;
        let q = amount / uprice;
        let v = (e.target.value - (e.target.value / 1.18)).toFixed(2);
        let TPTE = (e.target.value / 1.18).toFixed(2);
        let TPTI = (parseFloat(v) + parseFloat(TPTE));

        setQuantity(e.target.value / uprice);

        setTotPriceTaxEx(TPTE);

        setVat(v);

        setTotPriceTaxInc(TPTI);

    }

    function topup(e) {
        e.preventDefault();


        var formdata = new FormData();
        formdata.append("amount", amount);
        formdata.append("network", "MTN Mobile Money");
        formdata.append("phone", "0785283918");

        var requestOptions = {
            method: 'POST',
            headers: {
                'Authorization': `Token ${token}`,
            },
            body: formdata,
            redirect: 'follow'
        };

        fetch("http://127.0.0.1:8000/api/appecashtopup\n", requestOptions)
            .then(response => response.json())
            .then(result => console.log(result))
            .catch(error => console.log('error', error));
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

    // top up for regular customers

    var requestOptions1 = {
        method: 'POST',
        headers: {
            'Authorization': `Token ${token}`,
        },
        redirect: 'follow'
    };


    // Load MMN Network


    var requestOptions2 = {
        method: 'POST',
        headers: {
            'Authorization': `Token ${token}`,
        },
        redirect: 'follow'
    };


    useEffect(() => {
        // fetch networks
        fetch(API_URL + "getmmnetworks", requestOptions2)
            .then(response => response.json())
            .then(result => console.log(result.substring(2, result.lenght - 3)))
            .catch(error => console.log('error', error));


        // fetch user unit price
        fetch("http://127.0.0.1:8000/api/getuserunitprice", requestOptions1)
            .then(response => response.json())
            .then(result => setPriceInfo(JSON.parse(result)))
            .catch(error => console.log('error', error));

        // fetch published packages
        fetch("http://127.0.0.1:8000/api/getpublishedpackages", requestOptions)
            .then(response => response.json())
            .then(result => {
                setPublishedPackages(JSON.parse(result));
                setOptions(JSON.parse(result).response.map((option => ({ value: option.pk, label: option.fields.name, minprice: option.fields.minimumprice, maxprice: option.fields.maximumprice, unitprice: option.fields.unitpricetaxincl, minimum: option.fields.minimum, maximum: option.fields.maximum }))));
            })
            .catch(error => console.log('error', error));


    })

    const [options, setOptions] = useState();

    const [selectedOption, setSelectedOption] = useState(null)

    const handleChange = (selectedOption) => {
        setSelectedOption(selectedOption);
        console.log(`Option selected:`, selectedOption);
    };



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
                        {/* 

                        <Modal show={show} onHide={handleClose} className="modal">
                            <Modal.Header className='modal-header' closeButton>
                                <span className='contact-modal-title'><i className='fa fa-key'></i> TopUp Credit</span>
                            </Modal.Header>
                            <form onSubmit={topup} encType="multipart/form-data" method="POST">
                                <Modal.Body>


                                    <span className="">Payment Information</span>
                                    <hr />

                                    <div className='d-flex flex-row gap-2 mb-3'>
                                        <InputGroup>

                                            <InputGroup.Text>
                                                <BsCashCoin />
                                            </InputGroup.Text>
                                            <Form.Select aria-label="Default ">
                                           
                                                <option>Select an option</option>
                                                <option value="MTN Mobile Money">MTN Mobile Money </option>
                                                <option value="2">Airtel Money</option>

                                            </Form.Select>
                                        </InputGroup>

                                        <InputGroup>

                                            <InputGroup.Text>
                                                <BsPhoneVibrate />
                                            </InputGroup.Text>
                                            <Form.Control type='text' placeholder='Phone Number' required></Form.Control>
                                        </InputGroup>
                                    </div>
                                    <div className='d-flex flex-row gap-2 mb-3'>
                                        <InputGroup>

                                            <InputGroup.Text>
                                                <BsCash />
                                            </InputGroup.Text>
                                            <Form.Control
                                                type='text'
                                                value={amount}
                                                onChange={handleAmount}
                                            ></Form.Control>
                                        </InputGroup>

                                    </div>

                                    <span className="">Price Information</span>
                                    <hr />

                                    <div className='d-flex flex-row gap-2 mb-3'>
                                        <InputGroup>

                                            <InputGroup.Text className='w-50'>
                                                Unit Price:
                                            
                                            </InputGroup.Text>

                                            <Form.Control
                                                className='text-end'
                                                type='text'
                                                value={priceInfo && priceInfo.response.unitprice}
                                                // onChange={(e)=>setUnitPrice(e.target.value)}
                                                readOnly
                                            />
                                        </InputGroup>

                                    </div>
                                    <div className='d-flex flex-row gap-2 mb-3'>
                                        <InputGroup>

                                            <InputGroup.Text className='w-50'>
                                                Quantity :
                                     
                                            </InputGroup.Text>
                                            <Form.Control
                                                type='text'
                                                // value={quantity}
                                                value={quantity}


                                                // onChange={handleOnChangeQuantity}
                                                className='text-end'
                                                readOnly></Form.Control>
                                        </InputGroup>

                                    </div>
                                    <div className='d-flex flex-row gap-2 mb-3'>
                                        <InputGroup>

                                            <InputGroup.Text className='w-50'>
                                                Total Price Tax Excl. :
                                        
                                            </InputGroup.Text>
                                            <Form.Control
                                                type='text'
                                                value={totPriceTaxEx}
                                                className='text-end'
                                                readOnly />
                                        </InputGroup>

                                    </div>
                                    <div className='d-flex flex-row gap-2 mb-3'>
                                        <InputGroup>

                                            <InputGroup.Text className='w-50'>
                                                VAT(18%) :
                                              
                                            </InputGroup.Text>
                                            <Form.Control
                                                type='text'
                                                value={vat}
                                                className='text-end'
                                                readOnly></Form.Control>
                                        </InputGroup>

                                    </div>
                                    <div className='d-flex flex-row gap-2 mb-3'>
                                        <InputGroup>

                                            <InputGroup.Text className='w-50'>
                                                Total Price Tax Incl. :
                                       
                                            </InputGroup.Text>
                                            <Form.Control
                                                type='text'
                                                value={totPriceTaxInc}
                                                className='text-end'
                                                readOnly />
                                        </InputGroup>

                                    </div>

                                </Modal.Body>
                                <Modal.Footer>
                                    <button type="submit" className='btn btn-dark opacity-60'> Submit </button>
                                    <Button className='btn btn-dark opacity-60' onClick={handleClose}> Cancel</Button>

                                </Modal.Footer>
                            </form >

                        </Modal > * /}




{/* Add Modal */ }

                        <Modal show={show} onHide={handleClose} className="modal">
                            <Modal.Header className='modal-header' closeButton>
                                <span className='contact-modal-title'><i className='fa fa-key'></i> TopUp Credit</span>
                            </Modal.Header>
                            <Form >
                                <Modal.Body>



                                    <span>Price Information</span>
                                    <hr />
                                    <div className='d-flex flex-row gap-2 mb-3'>
                                        <InputGroup>

                                            <Select
                                                value={selectedOption}
                                                onChange={handleChange}
                                                options={options}
                                                className="modal-input mb-2"
                                            />

                                        </InputGroup>
                                    </div>
                                    {/* <div className='d-flex flex-row gap-2 mb-3'>
                                    <Select
                                        value={selectedOption}
                                        onChange={handleChange}
                                        options={options}
                                    />
                                    </div> */}


                                    <div className='d-flex flex-row gap-2 mb-3'>
                                        <InputGroup>

                                            <InputGroup.Text>
                                                Credits Range (From)
                                            </InputGroup.Text>
                                            <Form.Control type='text' className="text-right" placeholder='0.0' value={selectedOption && selectedOption.minimum}></Form.Control>
                                        </InputGroup>

                                        <InputGroup>
                                            <InputGroup.Text>

                                                To:
                                            </InputGroup.Text>
                                            <Form.Control type='text' className="text-right" placeholder='0.0' value={selectedOption && selectedOption.maximum}></Form.Control>
                                        </InputGroup>
                                    </div>


                                    <div className='d-flex flex-row gap-2 mb-3'>
                                        <InputGroup>

                                            <InputGroup.Text>
                                                Price Range (From)
                                            </InputGroup.Text>
                                            <Form.Control type='text' className="text-right" placeholder='0.0' value={selectedOption && selectedOption.minprice}></Form.Control>
                                        </InputGroup>

                                        <InputGroup>
                                            <InputGroup.Text>

                                                To:
                                            </InputGroup.Text>

                                            <Form.Control type='text' className="text-right" placeholder='0.0' value={selectedOption && selectedOption.maxprice}></Form.Control>
                                        </InputGroup>
                                    </div>


                                    <div className='d-flex flex-row gap-2 mb-3'>
                                        <InputGroup>

                                            <InputGroup.Text>

                                                Unit Price
                                            </InputGroup.Text>
                                            <Form.Control type='text' className="text-right" placeholder='0.0' value={selectedOption && selectedOption.unitprice}></Form.Control>
                                        </InputGroup>

                                        <InputGroup>
                                            <InputGroup.Text>
                                                Quantity
                                            </InputGroup.Text>

                                            <Form.Control type='textarea' className="text-right" placeholder='' required></Form.Control>
                                        </InputGroup>
                                    </div>

                                    <div className='d-flex flex-row gap-2 mb-3'>
                                        <InputGroup>

                                            <InputGroup.Text>

                                                Tot. Price Tax Excl.
                                            </InputGroup.Text>
                                            <Form.Control type='text' className="text-right" placeholder='' required></Form.Control>
                                        </InputGroup>

                                        <InputGroup>
                                            <InputGroup.Text>

                                                VAT (18%)
                                            </InputGroup.Text>

                                            <Form.Control type='text' className="text-right" placeholder='' required></Form.Control>
                                        </InputGroup>
                                    </div>

                                    <div className='d-flex flex-row gap-2 mb-3'>
                                        <InputGroup>

                                            <InputGroup.Text>

                                                Tot. Price Tax Excl.
                                            </InputGroup.Text>
                                            <Form.Control type='text' className="text-right" placeholder='' required></Form.Control>
                                        </InputGroup>

                                    </div>

                                    <span className="">Payment Information</span>
                                    <hr />

                                    <div className='d-flex flex-row gap-2 mb-3'>
                                        <InputGroup>

                                            <InputGroup.Text>
                                                <BsCashCoin />
                                            </InputGroup.Text>
                                            <Form.Control className='m-0 h-100' type='select' placeholder='Payment Method' required></Form.Control>
                                        </InputGroup>

                                        <InputGroup>

                                            <InputGroup.Text>
                                                <BsPhoneVibrate />
                                            </InputGroup.Text>
                                            <Form.Control type='text' placeholder='Phone Number' pattern="07[8-9]\d{6}|+2507[8-9]\d{6}" required></Form.Control>
                                        </InputGroup>
                                    </div>
                                    <div className='d-flex flex-row gap-2 mb-3'>
                                        <InputGroup>

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
                    </Stack >

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






                </div >



            </div >

        </div >

    )
}

export default Header

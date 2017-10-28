import * as env from './../environments/environment.prod';

const prod_services = {
    'test-service': 'http://foodbank-inventory-server.herokuapp.com/users/display-all',
    'auth': '',
    'admin': {
        'dashboard':'',
        'allocation':'',
        'inventory':'',
        'packing-list':''
    },
    'volunteer': {
        'dashboard':'',
        'inventory':'',
        'packing-list':''
    },
    'beneficiary': {
        'dashboard':'',
        'inventory':'',
        'request':''
    }
};

const dev_services = {
    'test-service': './../../assets/mock/test-service.json',
    'auth-success': './../../assets/mock/auth-success.json',
    'auth-fail': './../../assets/mock/auth-fail.json',
    'admin': {
        'dashboard':'',
        'allocation':'',
        'inventory':'',
        'packing-list':''
    },
    'volunteer': {
        'dashboard':'',
        'inventory':'',
        'packing-list':''
    },
    'beneficiary': {
        'dashboard':'./../../assets/mock/beneficiary/browse.json',
        'inventory':'',
        'request':''
    }
};

const services = env.environment.production ? prod_services : dev_services;

export default services;
